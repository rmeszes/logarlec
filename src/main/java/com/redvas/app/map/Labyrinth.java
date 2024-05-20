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
import com.redvas.app.ui.GeneratorListener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.logging.Logger;

public class Labyrinth implements Steppable {
    private final List<Door> everyDoor = new ArrayList<>();

    private static class Data {
        public boolean passable;
        public boolean vanished;
        public Direction direction;
    }

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
            Room r = (Room) ctor.newInstance(l, Integer.parseInt(room.getAttribute("id")), Integer.parseInt(room.getAttribute("capacity")));
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
                        Element roomItem = (Element) roomItems.item(k);
                        int roomItemID = Integer.parseInt(roomItem.getAttribute("id"));
                        ctor = Class.forName(roomItem.getAttribute("type")).getDeclaredConstructor(Integer.class, Room.class);
                        ctor.setAccessible(true);
                        Item it = (Item) ctor.newInstance(roomItemID, r);
                        items.put(it, roomItem);
                        id2item.put(it.getID(), it);
                    }
                } else if (sub.getTagName().equals("occupants")) {
                    NodeList occupants = ((Element) rooms.item(i)).getElementsByTagName("player");

                    for (int j = 0; j < occupants.getLength(); j++) {
                        Element occupant = (Element) occupants.item(j);

                        ctor = Class.forName(occupant.getAttribute("type")).getDeclaredConstructor(Integer.class, Room.class, Game.class);
                        Player p = (Player) ctor.newInstance(Integer.parseInt(occupant.getAttribute("id")),
                                l.rooms.get(Integer.parseInt(occupant.getAttribute("where"))),
                                g);
                        p.loadXML(occupant);

                        NodeList playerItems = occupant.getElementsByTagName("item");

                        for (int k = 0; k < playerItems.getLength(); k++) {
                            Element playerItem = (Element) playerItems.item(k);
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

            for (int m = 0; m < phantomListeners.getLength(); m++) {
                Element phantomListener = (Element) phantomListeners.item(m);
                ctor = Class.forName(phantomListener.getAttribute("type")).getDeclaredConstructor(Integer.class, Room.class, Boolean.class);
                ctor.setAccessible(true);
                ctor.newInstance(Integer.parseInt(phantomListener.getAttribute("id")), r, true);
            }
        }

        HashMap<Room, HashMap<Room, Data>> origins = new HashMap<>();
        rooms = labyrinth.getElementsByTagName("room");

        for (int i = 0; i < rooms.getLength(); i++) {
            Element room = (Element) rooms.item(i);

            NodeList doors = room.getElementsByTagName("door");


            for (int j = 0; j < doors.getLength(); j++) {
                Element door = (Element) doors.item(j);
                Data d = new Data();
                Room from = id2room.get(Integer.parseInt(room.getAttribute("id")));
                if (from.getID() == 2)
                    logger.fine("");
                Room to = id2room.get(Integer.parseInt(door.getAttribute("connects_to")));
                d.passable = Boolean.parseBoolean(door.getAttribute("is_passable"));
                d.vanished = Boolean.parseBoolean(door.getAttribute("is_vanished"));
                d.direction = Direction.valueOf(door.getAttribute("direction"));
                HashMap<Room, Data> sub;

                boolean makeEdge = false;

                if ((sub = origins.getOrDefault(to, null)) != null) {
                    Data other;

                    if (from.getID() == 2)
                        logger.fine("");

                    if ((other = sub.getOrDefault(from, null)) != null) {
                        l.everyDoor.add(new Door(from, to, d.direction, d.passable, other.passable));
                        l.everyDoor.get(l.everyDoor.size() - 1).setVanished(other.vanished);
                        sub.remove(from);
                    }
                    else makeEdge = true;
                } else makeEdge = true;
                if (makeEdge) {
                    if (from.getID() == 2)
                        logger.fine("");

                    if ((sub = origins.getOrDefault(from, null)) == null) {
                        sub = new HashMap<>();
                        origins.put(from, sub);
                    }

                    sub.put(to, d);
                }
            }
        }

        for (Map.Entry<Room, HashMap<Room, Data>> e : origins.entrySet())
            for (Map.Entry<Room, Data> e2 : e.getValue().entrySet()) {
                Door d = new Door(e.getKey(), e2.getKey(), e2.getValue().direction, e2.getValue().passable, false);
                d.setVanished(e2.getValue().vanished);
                l.everyDoor.add(d);
            }


        for (Map.Entry<Item, Element> e : items.entrySet())
            e.getKey().loadXML(e.getValue(), id2item);

        return l;
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

    public final Game game;
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

        protected int size() {
            return writingFront - contentStart;
        }

        protected MaxRandomPool(int maxSize) {
            lotteryNumbers = (T[]) new Object[maxSize];
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

    private Room[][] rooms2D;
    private final List<Room> rooms = new ArrayList<>();

    private <T> void swap(T[] array, int i, int j) {
        T tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    private <T, E, J, K> void shuffle(T[] array1, E[] array2, J[] array3, K[] array4) {

        for (int i = 0; i < array1.length - 1; i++) {
            int rid = random.nextInt(i, array1.length);

            if (rid != i) {
                swap(array1, i, rid);
                swap(array2, i, rid);
                swap(array3, i, rid);
                swap(array4, i, rid);
            }
        }
    }

    private void mkstat3(Boolean[] stat, int limCheck, int x, int y) {
        for (int i = 0; i < limCheck; i++)
            stat[i] = x + xc[i] >= 0 && x + xc[i] < width &&
                    y + yc[i] >= 0 && y + yc[i] < height;
    }

    private void mkstat2(Boolean[] stat, int limCheck, Room[][] rooms, Room[][] visits, int x, int y) {
        for (int i = 0; i < limCheck; i++)
            stat[i] = x + xc[i] >= 0 && x + xc[i] < width &&
                    y + yc[i] >= 0 && y + yc[i] < height &&
                    visits[y][x] != rooms[y + yc[i]][x + xc[i]] &&
                    visits[y + yc[i]][x + xc[i]] != rooms[y][x];
    }

    private int mkstat(Boolean[] stat, Room[][] visits, int x, int y) {
        int visitable = 0;

        for (int i = 0; i < 4; i++) {
            stat[i] = x + xc[i] >= 0 && x + xc[i] < width &&
                    y + yc[i] >= 0 && y + yc[i] < height &&
                    visits[y + yc[i]][x + xc[i]] == null;

            if (Boolean.TRUE.equals(stat[i])) visitable++;
        }

        return visitable;
    }

    // LEFT, UP, RIGHT, DOWN (x + deltax, y + deltay)
    Integer[] xc = new Integer[]{-1, 0, 1, 0}; // x axis delta
    Integer[] yc = new Integer[]{0, -1, 0, 1}; // y axis delta

    private void reset() {
        directions = new Direction[] {
                Direction.LEFT,
                Direction.UP,
                Direction.RIGHT,
                Direction.DOWN
        };
        xc = new Integer[]{-1, 0, 1, 0};
        yc = new Integer[]{0, -1, 0, 1};
    }

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
                shuffle(xc, yc, stat, directions);
                shuffle(xc, yc, stat, directions);
                shuffle(xc, yc, stat, directions);

                int letsVisit = random.nextInt(1, visitable + 1);

                for (int i = 0, visited = 0; i < 4 && visited <= letsVisit; i++) {
                    if (Boolean.TRUE.equals(stat[i])) {
                        visited++;
                        visits[pts.get(at).y + yc[i]][pts.get(at).x + xc[i]] = rooms[pts.get(at).y][pts.get(at).x];

                        everyDoor.add(new Door(
                                rooms[pts.get(at).y][pts.get(at).x], //from
                                rooms[pts.get(at).y + yc[i]][pts.get(at).x + xc[i]], // to
                                directions[i], // in direction
                                true
                        ));

                        listener.doorCreated(
                                everyDoor.get(everyDoor.size()  - 1),
                                pts.get(at).x, pts.get(at).y,
                                pts.get(at).x + xc[i], pts.get(at).y + yc[i]
                        );

                        pts.add(new PT(pts.get(at).x + xc[i], pts.get(at).y + yc[i]));
                    }
                }
            }

            if (pts.size() > 0) at = random.nextInt(0, pts.size());
        }
    }

    private Direction[] directions = new Direction[]{
            Direction.LEFT,
            Direction.UP,
            Direction.RIGHT,
            Direction.DOWN
    };
    // visitor pattern
    private Map<Direction, Door> selection;


    private boolean[][] resizify(Room[][] rooms) {
        boolean[][] resizingMap = new boolean[height][width];
        Boolean[] stat = new Boolean[2];
        reset();

        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                mkstat3(stat, 2, x, y);

                for (int i = 0; i < 2; i++)
                    if (Boolean.TRUE.equals(stat[i]) && Math.random() > 0.85 && resizeablePair(resizingMap, x, y, x + xc[i], y + yc[i])) {
                        rooms[y][x] = rooms[y][x].convertToResizing(directions[i], random.nextInt(2, 6));
                        if (listener != null)
                            listener.resizingRoomCreated((ResizingRoom) rooms[y][x], x, y);
                        resizingMap[y][x] = resizingMap[y + yc[i]][x + xc[i]] = true;
                    }
            }

        return resizingMap;
    }

    private void enchant(Room[][] rooms, boolean[][] resizingMap) {
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                if (!resizingMap[y][x]) {
                    if (random.nextGaussian() > 0.8) {
                        rooms[y][x] = rooms[y][x].convertToEnchanted(random.nextInt(2, 6));
                        if (listener != null)
                            listener.enchantedRoomCreated((EnchantedRoom) rooms[y][x], x, y);
                    }
                    else if (listener != null) listener.roomCreated(rooms[y][x], x, y);
                }
    }


    private void cyclicize(Room[][] rooms, Room[][] visits, boolean[][] resizingMap) {
        Boolean[] stat = new Boolean[4];
        reset();
        // after resetting, the first 2 directions are UP and LEFT

        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                mkstat2(stat, 2, rooms, visits, x, y);

                for (int k = 0; k < 2; k++)
                    if (Boolean.TRUE.equals(stat[k])) {
                        boolean makeEdge = random.nextDouble(0, 1) > 0.4;

                        everyDoor.add(new Door(
                                rooms[y][x],
                                rooms[y + yc[k]][x + xc[k]],
                                directions[k],
                                makeEdge,
                                random.nextDouble(0, 1) > 0.88
                        ));

                        if (listener != null)
                            listener.doorCreated(
                                    everyDoor.get(everyDoor.size()  - 1),
                                    x, y,
                                    x + xc[k], y + yc[k]
                                    );
                    }
            }
    }

    private void generate() {
        Room[][] roomsLocal = new Room[height][width];

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                roomsLocal[i][j] = new Room(this, i * width + j, random.nextInt(2, 6));

        Room[][] visits = new Room[height][width];
        Random r = random;
        int ry = r.nextInt(0, height);
        int rx = r.nextInt(0, width);
        visits[ry][rx] = roomsLocal[ry][rx];
        boolean[][] map = new boolean[height][width];
        randomOrderSearch(roomsLocal, visits, map, rx, ry);
        cyclicize(roomsLocal, visits, map);

        /*boolean[][] resizingMap = resizify(roomsLocal);
        enchant(roomsLocal, resizingMap);*/

        rooms2D = roomsLocal;
        // TODO: TO BE REMOVED IN THE FUTURE!!!!!! (NOT FOR NOW, THO...)
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                rooms.add(roomsLocal[y][x]);
                if (listener != null)
                    listener.roomCreated(roomsLocal[y][x], x,y);
            }
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
        game.registerSteppable(r);
    }

    public void forget(Room room) {
        rooms.remove(room);
    }

    private final int height;
    private final int width;
    private GeneratorListener listener = null;

    private Labyrinth(int width, int height, Game game) {
        if (height < 1) height = 1;
        if (width < 1) width = 1;
        this.height = height;
        this.width = width;
        this.game = game;
    }

    public Labyrinth(int width, int height, int playerCount, Game game) {
        this(width, height, game);
        generate();
        emplacePlayers(playerCount);
        emplaceItems();
    }

    public Labyrinth(int width, int height, Game game, int playerCount, GeneratorListener listener) {
        this(width, height, game);
        this.listener = listener;
        generate();
        emplacePlayers(playerCount);
        emplaceItems();
    }

    /**
     * calls the update on every object
     * and asks players to move (later)
     */
    @Override
    public void step() {
        logger.fine("Labyrinth step");

        for (Room r : rooms)
            r.step();
    }

    private int randomX() {
        return random.nextInt(width);
    }

    private int randomY() {
        return random.nextInt(height);
    }

    private void emplacePlayers(int playerCount) {
        int nextId = 1;
        logger.fine("Placing players..");

        for (int i = 1; i <= playerCount; i++) {
            int rx = randomX();
            int ry = randomY();
            Undergraduate u = new Undergraduate(nextId++, rooms2D[ry][rx], game);
            Undergraduate u2 = new Undergraduate(nextId++, rooms2D[ry][rx], game);
            Undergraduate u3 = new Undergraduate(nextId++, rooms2D[ry][rx], game);
            Undergraduate u4 = new Undergraduate(nextId++, rooms2D[ry][rx], game);

            game.registerSteppable(u);
            game.registerSteppable(u2);
            game.registerSteppable(u3);
            game.registerSteppable(u4);

            if (listener != null)
                listener.undergraduateCreated(u, rx, ry);
            if (listener != null)
                listener.undergraduateCreated(u2, rx, ry);
            if (listener != null)
                listener.undergraduateCreated(u3, rx, ry);
            if (listener != null)
                listener.undergraduateCreated(u4, rx, ry);
        }

        int professorCount = random.nextInt(1, playerCount);

        for (int i = 1; i <= professorCount; i++) {
            int rx = randomX();
            int ry = randomY();
            Professor p = new Professor(nextId, rooms2D[ry][rx], game);
            game.registerSteppable(p);

            if (listener != null)
                listener.professorCreated(p, rx, ry);
        }

        int janitorCount = random.nextInt(1, playerCount);

        for (int i = 1; i <= janitorCount; i++) {
            int rx = randomX();
            int ry = randomY();
            Janitor j = new Janitor(nextId, rooms2D[ry][rx], game);
            game.registerSteppable(j);

            if (listener != null)
                listener.janitorCreated(j, rx, ry);
        }
    }

    /**
     * I tried to do this, I modified it, so it does not use getRandomRoom but generates two ints and uses rooms2D
     * because it needs to be stored (the x and y values) in order to draw the items in the correct room
     */
    private void emplaceItems() {
        logger.fine("Placing items..");

        Map<String, Integer> numOfItems = howManyItems();

        for (int i = 0; i < numOfItems.get("AirFreshener"); i++) {
            int rx = randomX();
            int ry = randomY();
            AirFreshener a = new AirFreshener(1, rooms2D[ry][rx]);
            if (listener != null) listener.airFreshenerCreated(a, rx, ry);
        }
        for (int i = 0; i < numOfItems.get("FFP2"); i++) {
            int rx = randomX();
            int ry = randomY();
            FFP2 f = new FFP2(2, rooms2D[ry][rx]);
            if (listener != null) listener.ffp2Created(f, rx, ry);
        }
        for (int i = 0; i < numOfItems.get("HolyBeer"); i++){
            int rx = randomX();
            int ry = randomY();
            HolyBeer hb = new HolyBeer(3, rooms2D[ry][rx]);
            if (listener != null) listener.holyBeerCreated(hb, rx, ry);
        }
        for (int i = 0; i < numOfItems.get("RottenCamembert"); i++) {
            int rx = randomX();
            int ry = randomY();
            RottenCamembert rc = new RottenCamembert(4, rooms2D[ry][rx]);
            if (listener != null) listener.rottenCamembertCreated(rc, rx, ry);
        }
        for (int i = 0; i < numOfItems.get("Transistor"); i++) {
            int rx = randomX();
            int ry = randomY();
            Transistor t = new Transistor(5, rooms2D[ry][rx]);
            if (listener != null) listener.transistorCreated(t, rx, ry);
        }
        for (int i = 0; i < numOfItems.get("TVSZ"); i++) {
            int rx = randomX();
            int ry = randomY();
            TVSZ tvsz = new TVSZ(6, rooms2D[ry][rx]);
            if (listener != null) listener.tvszCreated(tvsz, rx, ry);

        }
        for (int i = 0; i < numOfItems.get("WetWipe"); i++) {
            int rx = randomX();
            int ry = randomY();
            WetWipe ww = new WetWipe(7, rooms2D[ry][rx]);
            if (listener != null) listener.wetWipeCreated(ww, rx, ry);
        }
    }

    private Map<String, Integer> howManyItems() {
        String transistor = "Transistor";

        HashMap<String, Integer> numOfItems = HashMap.newHashMap(6);

        numOfItems.put("AirFreshener", random.nextInt(0, 4));
        numOfItems.put("FFP2", random.nextInt(0, 4));
        numOfItems.put("HolyBeer", random.nextInt(0, 4));
        numOfItems.put("RottenCamembert", random.nextInt(0, 4));
        numOfItems.put(transistor, random.nextInt(0, 5));
        numOfItems.put("TVSZ", random.nextInt(0, 4));
        numOfItems.put("WetWipe", random.nextInt(0, 4));

        //Makes num of transistors even
        if (numOfItems.get(transistor) % 2 != 0) {
            numOfItems.replace(transistor, numOfItems.get(transistor) - 1);
        }
        return numOfItems;
    }

    private Room getRandomRoom() {
        if (rooms.isEmpty()) throw new NoSuchElementException("Rooms not created yet!");
        return rooms.get(random.nextInt(0, rooms.size()));
    }
}
