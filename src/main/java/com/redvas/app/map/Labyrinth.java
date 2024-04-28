package com.redvas.app.map;

import com.redvas.app.App;
import com.redvas.app.Game;
import com.redvas.app.Steppable;
import com.redvas.app.items.*;
import com.redvas.app.map.rooms.EnchantedRoom;
import com.redvas.app.map.rooms.ResizingRoom;
import com.redvas.app.map.rooms.Room;
import com.redvas.app.players.Janitor;
import com.redvas.app.players.Player;
import com.redvas.app.players.Professor;
import com.redvas.app.players.Undergraduate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.logging.Logger;


public class Labyrinth implements Steppable {
    public static Labyrinth loadXML(Element labyrinth, Game g) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        NodeList rooms = labyrinth.getElementsByTagName("room");
        Labyrinth l = new Labyrinth(
                Integer.parseInt(labyrinth.getAttribute("width")),
                Integer.parseInt(labyrinth.getAttribute("height")),
                g
        );

        Constructor<?> ctor;
        HashMap<Item, Element> items = new HashMap<>();
        HashMap<Integer, Room> id2room = new HashMap<>();
        HashMap<Integer, Item> id2item = new HashMap<>();

        for (int i = 0; i < rooms.getLength(); i++) {
            Element room = (Element) rooms.item(i);
            ctor = Class.forName(room.getAttribute("type")).getDeclaredConstructor(Labyrinth.class, Integer.class, Integer.class);
            ctor.setAccessible(true);
            Room r = (Room)ctor.newInstance(l, Integer.parseInt(room.getAttribute("id")), Integer.parseInt(room.getAttribute("capacity")));
            r.loadXML(room);
            l.rooms.add(r);
            id2room.put(r.getID(), r);

            NodeList subs = rooms.item(i).getChildNodes();

            for (int m = 0; m < subs.getLength(); m++) {
                if (subs.item(m).getNodeType() != Node.ELEMENT_NODE) continue;
                Element sub = (Element) subs.item(m);

                if (sub.getTagName().equals("items")) {
                    NodeList roomItems = sub.getElementsByTagName("item");

                    for (int k = 0; k < roomItems.getLength(); k++) {
                        Element roomItem = (Element)roomItems.item(k);
                        int roomItemID = Integer.parseInt(roomItem.getAttribute("id"));
                        ctor = Class.forName(roomItem.getAttribute("type")).getDeclaredConstructor(Integer.class, Room.class);
                        ctor.setAccessible(true);
                        Item it = (Item) ctor.newInstance(roomItemID, r);
                        items.put(it, roomItem);
                        id2item.put(it.getID(), it);
                    }
                }
                else if (sub.getTagName().equals("occupants")) {
                    NodeList occupants = ((Element)rooms.item(i)).getElementsByTagName("player");

                    for (int j = 0; j < occupants.getLength(); j++) {
                        Element occupant = (Element)occupants.item(j);

                        ctor = Class.forName(occupant.getAttribute("type")).getDeclaredConstructor(Integer.class, Room.class, Game.class);
                        Player p = (Player)ctor.newInstance(Integer.parseInt(occupant.getAttribute("id")),
                                l.rooms.get(Integer.parseInt(occupant.getAttribute("where"))),
                                g);
                        p.loadXML(occupant);

                        NodeList playerItems = occupant.getElementsByTagName("item");

                        for (int k = 0; k < playerItems.getLength(); k++) {
                            Element playerItem = (Element)playerItems.item(k);
                            int playerItemID = Integer.parseInt(playerItem.getAttribute("id"));
                            ctor = Class.forName(playerItem.getAttribute("type")).getDeclaredConstructor(Integer.class, Player.class);
                            ctor.setAccessible(true);
                            Item it = (Item) ctor.newInstance(playerItemID, p);
                            items.put(it, playerItem);
                            id2item.put(it.getID(), it);
                        }
                    }
                }
            }

            NodeList phantomListeners = room.getElementsByTagName("phantom_listener");

            for (int m= 0; m < phantomListeners.getLength(); m++) {
                Element phantomListener = (Element) phantomListeners.item(m);
                ctor = Class.forName(phantomListener.getAttribute("type")).getDeclaredConstructor(Integer.class, Room.class, Boolean.class);
                ctor.setAccessible(true);
                ctor.newInstance(Integer.parseInt(phantomListener.getAttribute("id")), r, true);
            }
        }


        for (int i = 0; i < rooms.getLength(); i++) {
            NodeList doors = ((Element) rooms.item(i)).getElementsByTagName("door");

            for (int j = 0; j < doors.getLength(); j++) {
                Element door = ((Element) doors.item(j));
                l.rooms.get(i).configureDoors();
                Door d;
                l.selection.put(Direction.valueOf(door.getAttribute("direction")),
                        d = new Door(id2room.get(
                                Integer.parseInt(door.getAttribute("connects_to"))),
                                Boolean.parseBoolean(door.getAttribute("is_passable")))
                );
                d.setVanished(Boolean.parseBoolean(door.getAttribute("is_vanished")));
            }
        }

        for (Map.Entry<Item, Element> e : items.entrySet())
            e.getKey().loadXML(e.getValue(), id2item);

        return l;
    }

    public Labyrinth(int width, int height, Game game) {
        if(height < 1) height = 1;
        if(width < 1) width = 1;
        this.height = height;
        this.width = width;
        this.game = game;
    }

    public Element saveXML(Document document) {
        Element labyrinth = document.createElement("labyrinth");
        labyrinth.setAttribute("width", String.valueOf(width));
        labyrinth.setAttribute("height", String.valueOf(height));
        Element roomsXML = document.createElement("rooms");

        for (Room r : this.rooms)
            roomsXML.appendChild(r.saveXML(document));

        labyrinth.appendChild(roomsXML);
        return labyrinth;
    }

    private final Game game;
    private static final Random random = new Random();

    private static class PT {
        protected int x;
        protected int y;

        public PT(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // MaxRandomPool provides O(1) indexed (random/direct) access, O(1) indexed deletion, O(1) append so long as no more items are inserted than its maximum capacity
    private static class MaxRandomPool<T> {
        T[] lotteryNumbers;
        private int writingFront = 0;
        private int contentStart = 0;

        protected int size() { return writingFront - contentStart; }

        protected MaxRandomPool(int maxSize) {
            lotteryNumbers = (T[])new Object[maxSize];
        }

        protected void add(T pt) {
            lotteryNumbers[writingFront++] = pt;
        }

        protected void remove(int index) {
            index += contentStart;

            if (index >= contentStart && index < writingFront) {
                if (index != contentStart) {
                    T tmp = lotteryNumbers[contentStart];
                    lotteryNumbers[contentStart] = lotteryNumbers[index];
                    lotteryNumbers[index] = tmp;
                }

                contentStart++;
            }
        }

        protected T get(int index) {
            index += contentStart;

            if (index >= contentStart && index < writingFront)
                return lotteryNumbers[index];
            return null;
        }

    }

    protected static final Logger logger = App.getConsoleLogger(Labyrinth.class.getName());

    private final List<Room> rooms = new ArrayList<>();

    private <T> void swap(T[] array, int i, int j) {
        T tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    private <T, E, J, K, L> void shuffle(T[] array1, E[] array2, J[] array3, K[] array4, L[] array5) {

        for (int i = 0; i < array1.length - 1; i++) {
            int rid = random.nextInt(i, array1.length);

            if (rid != i) {
                swap(array1, i, rid);
                swap(array2, i, rid);
                swap(array3, i, rid);
                swap(array4, i, rid);
                swap(array5, i, rid);
            }
        }
    }

    private void mkstat2(Boolean[] stat, Room[][] rooms, Room[][] visits, int x, int y) {

        for (int i = 0; i < 4; i++)
            stat[i] = x + xc[i] >= 0 && x + xc[i] < width &&
                    y + yc[i] >= 0 && y + yc[i] < height &&
                    visits[y][x] != rooms[y + yc[i]][x + xc[i]] &&
                    visits[y + yc[i]][x + xc[i]] != rooms[y][x];
    }

    private int mkstat(Boolean[] stat, Room[][] visits, int x, int y) {
        int visitable= 0;

        for (int i = 0; i < 4; i++) {
            stat[i] = x + xc[i] >= 0 && x + xc[i] < width &&
                    y + yc[i] >= 0 && y + yc[i] < height &&
                    visits[y + yc[i]][x + xc[i]] == null;

            if (Boolean.TRUE.equals(stat[i])) visitable++;
        }

        return visitable;
    }

    // LEFT, UP, RIGHT, DOWN (x + deltax, y + deltay)
    Integer[] xc = new Integer[] { -1, 0, 1, 0 }; // x axis delta
    Integer[] yc = new Integer[] { 0, -1, 0, 1 }; // y axis delta

    private boolean resizeablePair(boolean[][] resizingMap, int x1, int y1, int x2, int y2) {
       boolean neighboursOkay = true;

        for (int i = 0; i < 4; i++) { // Implication pattern A -> B = !A || B
            neighboursOkay &= !(x1 + xc[i] >= 0 && x1 + xc[i] < width && y1 + yc[i] >= 0 && y1 + yc[i] < height) || (!resizingMap[y1 + yc[i]][x1 + xc[i]]);
            neighboursOkay &= !(x2 + xc[i] >= 0 && x2 + xc[i] < width && y2 + yc[i] >= 0 && y2 + yc[i] < height) || (!resizingMap[y2 + yc[i]][x2 + xc[i]]);
        }

        return !resizingMap[y1][x1] &&
                !resizingMap[y2][x2] &&
                neighboursOkay;
    }

    // Random Order Search
    private void randomOrderSearch(Room[][] rooms, Room[][] visits, boolean[][] resizingMap, int x, int y) {
        // PTList provides O(1) access, O(1) append, O(1) deletion
        MaxRandomPool<PT> pts = new MaxRandomPool<>(width * height);
        pts.add(new PT(x, y));
        int at = 0;
        Boolean[] stat = new Boolean[4];

        while (pts.size() != 0) {
            int visitable = mkstat(stat, visits, pts.get(at).x, pts.get(at).y);

            if (visitable == 0)
                pts.remove(at);
            else {
                shuffle(xc, yc, stat, directions, rdirections);
                shuffle(xc, yc, stat, directions, rdirections);
                shuffle(xc, yc, stat, directions, rdirections);

                int letsVisit = random.nextInt(1, visitable + 1);

                for (int i = 0, visited = 0; i < 4 && visited <= letsVisit; i++) {
                    if (Boolean.TRUE.equals(stat[i])) {
                        visited++;
                        visits[pts.get(at).y + yc[i]][pts.get(at).x + xc[i]] = rooms[pts.get(at).y][pts.get(at).x];

                        rooms[pts.get(at).y][pts.get(at).x].configureDoors();
                        selection.put(directions[i], new Door(rooms[pts.get(at).y + yc[i]][pts.get(at).x + xc[i]], true));

                        rooms[pts.get(at).y + yc[i]][pts.get(at).x + xc[i]].configureDoors();
                        selection.put(rdirections[i], new Door(rooms[pts.get(at).y][pts.get(at).x], true));

                        if (resizeablePair(resizingMap, pts.get(at).x, pts.get(at).y, pts.get(at).x + xc[i], pts.get(at).y + yc[i]) && Math.abs(random.nextGaussian()) > 0.81) {
                                ResizingRoom er = new ResizingRoom(rooms[pts.get(at).y][pts.get(at).x].getID(), this, random.nextInt(2, 6), directions[i]);
                                rooms[pts.get(at).y][pts.get(at).x].configureDoors();
                                message = selection;
                                er.receiveDoors();
                                rooms[pts.get(at).y + yc[i]][pts.get(at).x + xc[i]].configureDoors();
                                selection.put(rdirections[i], new Door(er, true));
                                rooms[pts.get(at).y][pts.get(at).x] = er;
                                resizingMap[pts.get(at).y][pts.get(at).x] = resizingMap[pts.get(at).y + yc[i]][pts.get(at).x + xc[i]] = true;
                            }

                        pts.add(new PT(pts.get(at).x + xc[i], pts.get(at).y + yc[i]));
                    }
                }
            }

            if(pts.size() > 0) at = random.nextInt(0, pts.size());
        }
    }

    private final Direction[] directions = new Direction[] {
        Direction.LEFT,
        Direction.UP,
        Direction.RIGHT,
        Direction.DOWN
    };
    private final Direction[] rdirections = new Direction[] {
            Direction.RIGHT,
            Direction.DOWN,
            Direction.LEFT,
            Direction.UP
    };
    // visitor pattern
    private Map<Direction, Door> selection;
    public Map<Direction, Door> sendDoors() { return message;}
    private Map<Direction, Door> message;
    public void acceptDoors(Map<Direction, Door> doors) {
        selection = doors;
    }

    private void enchant() {
        for (int i = 0; i < width * height; i++)
            if (random.nextGaussian() > 0.8) {
                EnchantedRoom er = new EnchantedRoom(this, rooms.get(i).getID(), random.nextInt(2, 6));
                rooms.get(i).configureDoors();

                Set<Map.Entry<Direction, Door>> doors = selection.entrySet();

                for (Map.Entry<Direction, Door> e : doors) {
                    e.getValue().setVanished(random.nextBoolean());
                    e.getValue().connectsTo().configureDoors();
                    selection.get(reverseDirections.get(e.getKey())).setVanished(e.getValue().isVanished());
                    selection.get(reverseDirections.get(e.getKey())).setConnection(er);
                    er.configureDoors();
                    selection.put(e.getKey(), e.getValue());
                }

                rooms.add(i, er);
            }
        logger.fine("Labyrinth created");
    }

    private void cyclify(Room[][] rooms, Room[][] visits, boolean[][] resizingMap) {
        Boolean[] stat = new Boolean[4];

        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                mkstat2(stat, rooms, visits, x, y);

                for (int k = 0; k < 4; k++)
                    if (Boolean.TRUE.equals(stat[k])) {
                        rooms[y][x].configureDoors();
                        boolean makeEdge= random.nextDouble(0, 1) > 0.88;
                        selection.put(directions[k], new Door(rooms[y + yc[k]][x + xc[k]], makeEdge));

                        if (makeEdge) {
                            rooms[y + yc[k]][x + xc[k]].configureDoors();

                            if (resizeablePair(resizingMap, x, y, x + xc[k], y + yc[k]) && Math.abs(random.nextGaussian()) > 0.81) {
                                    ResizingRoom er = new ResizingRoom(rooms[y][x].getID(), this, random.nextInt(2, 6), directions[k]);
                                    rooms[y][x].configureDoors();
                                    message = selection;
                                    er.receiveDoors();
                                    rooms[y + yc[k]][x + xc[k]].configureDoors();
                                    selection.put(rdirections[k], new Door(er, true));
                                    rooms[y][x] = er;
                                    resizingMap[y][x] = resizingMap[y + yc[k]][x + xc[k]] = true;
                                }
                        }
                    }
            }
    }

    private void generate() {
        Room[][] roomsLocal = new Room[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Room room = new Room(this, i * width + j, random.nextInt(2, 6));
                roomsLocal[i][j] = room;
                remember(room);
            }
        }

        Room[][] visits = new Room[height][width];
        Random r = random;
        int ry = r.nextInt(0, height);
        int rx = r.nextInt(0, width);
        visits[ry][rx] = roomsLocal[ry][rx];
        boolean[][] map = new boolean[height][width];
        randomOrderSearch(roomsLocal, visits, map, rx, ry);
        cyclify(roomsLocal, visits, map);

        for (int y = 0; y < height; y++)
            rooms.addAll(Arrays.asList(roomsLocal[y]).subList(0, width));

        enchant();
    }

    protected static HashMap<Direction, Direction> reverseDirections = new HashMap<>();


    static {
        reverseDirections.put(Direction.UP, Direction.DOWN);
        reverseDirections.put(Direction.DOWN, Direction.UP);
        reverseDirections.put(Direction.LEFT, Direction.RIGHT);
        reverseDirections.put(Direction.RIGHT, Direction.LEFT);
    }

    public void remember(Room r) {
        rooms.add(r);
    }

    public void forget(Room room) {
        rooms.remove(room);
    }

    private final int height;
    private final int width;
    public Labyrinth(int width, int height, Game game, String player1Name, String player2Name) {
        this(width, height, game);
        generate();
        emplacePlayers(player1Name,player2Name);
        emplaceItems();
    }

    /** calls the update on every object
     * and asks players to move (later)
     *
     */
    @Override
    public void step() {
        logger.fine("Labyrinth step");

        for (Room r : rooms)
            r.step();
    }

    private void emplacePlayers(String player1Name, String player2Name) {
        logger.fine("Placing players..");

        Room player1Place = getRandomRoom();
        Room player2Place = getRandomRoom();

        Undergraduate u1 = new Undergraduate(0, player1Name,player1Place, game);
        game.registerSteppable(u1);

        Undergraduate u2 = new Undergraduate(1, player2Name,player2Place, game);
        game.registerSteppable(u2);

        game.registerSteppable(new Professor(2, getRandomRoom(),game));
        game.registerSteppable(new Professor(3, getRandomRoom(),game));

        game.registerSteppable(new Janitor(4, getRandomRoom(),game));
    }

    private void emplaceItems() {
        logger.fine("Placing items..");

        Map<String, Integer> numOfItems = howManyItems();

        for(int i = 0; i < numOfItems.get("AirFreshener"); i++) new AirFreshener(1, getRandomRoom());
        for(int i = 0; i < numOfItems.get("FFP2"); i++) new FFP2(2, getRandomRoom());
        for(int i = 0; i < numOfItems.get("HolyBeer"); i++) new HolyBeer(3, getRandomRoom());
        for(int i = 0; i < numOfItems.get("RottenCamembert"); i++) new RottenCamembert(4, getRandomRoom());
        for(int i = 0; i < numOfItems.get("Transistor"); i++) new Transistor(5, getRandomRoom());
        for(int i = 0; i < numOfItems.get("TVSZ"); i++) new TVSZ(6, getRandomRoom());
        for(int i = 0; i < numOfItems.get("WetWipe"); i++) new WetWipe(7, getRandomRoom());
    }

    private Map<String,Integer> howManyItems() {
        String transistor = "Transistor";

        HashMap<String,Integer> numOfItems = HashMap.newHashMap(6);

        numOfItems.put("AirFreshener", random.nextInt(0,4));
        numOfItems.put("FFP2", random.nextInt(0,4));
        numOfItems.put("HolyBeer", random.nextInt(0,4));
        numOfItems.put("RottenCamembert", random.nextInt(0,4));
        numOfItems.put(transistor, random.nextInt(0,5));
        numOfItems.put("TVSZ", random.nextInt(0,4));
        numOfItems.put("WetWipe", random.nextInt(0,4));

        //Makes num of transistors even
        if(numOfItems.get(transistor) % 2 != 0) {
            numOfItems.replace(transistor, numOfItems.get(transistor) - 1);
        }
        return numOfItems;
    }

    private Room getRandomRoom() {
        if(rooms.isEmpty()) throw new NoSuchElementException("Rooms not created yet!");
        return rooms.get(random.nextInt(0,rooms.size()));
    }
}
