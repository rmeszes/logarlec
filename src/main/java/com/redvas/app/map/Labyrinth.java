package com.redvas.app.map;

import com.redvas.app.App;
import com.redvas.app.Game;
import com.redvas.app.Steppable;
import com.redvas.app.items.*;
import com.redvas.app.map.Rooms.EnchantedRoom;
import com.redvas.app.map.Rooms.ResizingRoom;
import com.redvas.app.map.Rooms.Room;
import com.redvas.app.players.Janitor;
import com.redvas.app.players.Professor;
import com.redvas.app.players.Undergraduate;

import java.util.*;

import java.util.logging.Logger;


public class Labyrinth implements Steppable {
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
                T tmp = lotteryNumbers[contentStart];
                lotteryNumbers[contentStart] = lotteryNumbers[index];
                lotteryNumbers[index] = tmp;
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
        // List<PT> pts = new ArrayList<>();
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

                        if (resizeablePair(resizingMap, pts.get(at).x, pts.get(at).y, pts.get(at).x + xc[i], pts.get(at).y + yc[i]))
                            if (Math.abs(random.nextGaussian()) > 0.88) {
                                ResizingRoom er = new ResizingRoom(this, directions[i]);
                                rooms[pts.get(at).y][pts.get(at).x].configureDoors();
                                message = selection;
                                er.receiveDoors();
                                rooms[pts.get(at).y + yc[i]][pts.get(at).x + xc[i]].configureDoors();
                                selection.put(rdirections[i], new Door(er, true));
                                rooms[pts.get(at).y][pts.get(at).x] = er;
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
    private HashMap<Direction, Door> selection;
    public HashMap<Direction, Door> sendDoors() { return message;}
    private HashMap<Direction, Door> message;
    public void acceptDoors(HashMap<Direction, Door> doors) {
        selection = doors;
    }

    private void cyclify(Room[][] rooms, Room[][] visits, boolean[][] resizingMap) {
        Boolean[] stat = new Boolean[4];

        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                mkstat2(stat, rooms, visits, x, y);

                for (int k = 0; k < 4; k++) {
                    if (Boolean.TRUE.equals(stat[k]) && random.nextDouble(0, 1) > 0.88) {
                        rooms[y][x].configureDoors();
                        selection.put(directions[k], new Door(rooms[y + yc[k]][x + xc[k]], true));
                        rooms[y + yc[k]][x + xc[k]].configureDoors();

                        if (selection.getOrDefault(rdirections[k], null) != null) {
                            // this means creating this edge made a counter directional link
                            if (resizeablePair(resizingMap, x, y, x + xc[k], y + yc[k]))
                                if (Math.abs(random.nextGaussian()) > 0.88) {
                                    ResizingRoom er = new ResizingRoom(this, directions[k]);
                                    rooms[y][x].configureDoors();
                                    message = selection;
                                    er.receiveDoors();
                                    rooms[y + yc[k]][x + xc[k]].configureDoors();
                                    selection.put(rdirections[k], new Door(er, true));
                                    rooms[y][x] = er;
                                }
                        }
                    }
                }
            }
    }

    private void generate() {
        Room[][] roomsLocal = new Room[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Room room = new Room(this);
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

        for (int i = 0; i < width * height; i++)
            if (random.nextGaussian() > 0.88) {
                EnchantedRoom er = new EnchantedRoom(this);
                rooms.get(i).configureDoors();

                for (Map.Entry<Direction, Door> e : selection.entrySet()) {
                    e.getValue().setVanished(random.nextBoolean());
                    e.getValue().connectsTo().configureDoors();
                    selection.get(reverseDirections.get(e.getKey())).setVanished(e.getValue().isVanished());
                    selection.get(reverseDirections.get(e.getKey())).setConnection(er);
                    er.configureDoors();
                    selection.put(e.getKey(), e.getValue());
                }

                rooms.add(i, er);
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
    }

    public void forget(Room room) {
        rooms.remove(room);
    }

    private void update() {
        logger.fine("Labyrinth is changing.");


    }

    private final int height;
    private final int width;
    public Labyrinth(int width, int height, Game game, String player1Name, String player2Name) {
        if(height < 1) height = 1;
        if(width < 1) width = 1;
        this.height = height;
        this.width = width;
        generate();
        logger.fine("Labyrinth created");
        this.game = game;
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

        update();
    }

    private void emplacePlayers(String player1Name, String player2Name) {
        logger.fine("Placing players..");

        Room player1Place = getRandomRoom();
        Room player2Place = getRandomRoom();

        new Undergraduate(player1Name,player1Place, game);

        new Undergraduate(player2Name,player2Place, game);

        game.registerSteppable(new Professor(getRandomRoom(),game));
        game.registerSteppable(new Professor(getRandomRoom(),game));

        game.registerSteppable(new Janitor(getRandomRoom(),game));
    }

    private void emplaceItems() {
        logger.fine("Placing items..");

        Map<String, Integer> numOfItems = howManyItems();

        for(int i = 0; i < numOfItems.get("AirFreshener"); i++) new AirFreshener(getRandomRoom());
        for(int i = 0; i < numOfItems.get("FFP2"); i++) new FFP2(getRandomRoom());
        for(int i = 0; i < numOfItems.get("HolyBeer"); i++) new HolyBeer(getRandomRoom());
        for(int i = 0; i < numOfItems.get("RottenCamembert"); i++) new RottenCamembert(getRandomRoom());
        for(int i = 0; i < numOfItems.get("Transistor"); i++) new Transistor(getRandomRoom());
        for(int i = 0; i < numOfItems.get("TVSZ"); i++) new TVSZ(getRandomRoom());
        for(int i = 0; i < numOfItems.get("WetWipe"); i++) new WetWipe(getRandomRoom());
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
