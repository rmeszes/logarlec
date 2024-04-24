package com.redvas.app.map;

import com.redvas.app.Steppable;

import java.lang.reflect.Array;
import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;



public class Labyrinth implements Steppable {
    class pt {
        public int x, y;

        public pt(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }


    protected static final Logger logger = Logger.getLogger("Labyrinth");

    static {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINEST);
        logger.addHandler(handler);
        logger.setLevel(Level.FINEST);
    }

    private final List<Room> rooms = new ArrayList<>();

    private <T> void swap(T[] array, int i, int j) {
        T tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    private <T, E, J> void shuffle(T[] array1, E[] array2, J[] array3) {
        Random r = new Random();

        for (int i = 0; i < array1.length - 1; i++) {
            int rid = r.nextInt(i, array1.length);

            if (rid != i) {
                swap(array1, i, rid);
                swap(array2, i, rid);
                swap(array3, i, rid);
            }
        }
    }

    private void mkstat2(Boolean[] stat, Room[][] rooms, Room[][] visits, int x, int y) {
        stat[0] = stat[1] = stat[2] = stat[3] = false;

        if (x > 0 && visits[y][x - 1] != rooms[y][x] && visits[y][x] != rooms[y][x - 1]) {
            stat[0] = true; // left
        }
        if (y > 0 && visits[y - 1][x] != rooms[y][x] && visits[y][x] != rooms[y - 1][x]) {
            stat[0] = true; // left
        }
        if (x + 1 < visits[0].length && visits[y][x + 1] != rooms[y][x] && visits[y][x] != rooms[y][x + 1]) {
            stat[0] = true; // left
        }
        if (y + 1 < visits.length && visits[y + 1][x] != rooms[y][x] && visits[y][x] != rooms[y + 1][x]) {
            stat[0] = true; // left
        }
    }

    private int mkstat(Boolean[] stat, Room[][] visits, int x, int y) {
        stat[0] = stat[1] = stat[2] = stat[3] = false;
        int visitable= 0;

        if (x > 0 && visits[y][x - 1] == null) {
            stat[0] = true; // left
            visitable++;

        }
        if (y > 0 && visits[y - 1][x] == null) {
            stat[1] = true; // up
            visitable++;
        }
        if (x + 1 < visits[0].length && visits[y][x + 1] == null) {
            stat[2] = true; // right
            visitable++;
        }
        if (y + 1 < visits.length && visits[y + 1][x] == null) {
            stat[3] = true; // down
            visitable++;
        }

        return visitable;
    }

    private void rxc(Integer[] xc) {
        xc[0] = -1;
        xc[1] = 0;
        xc[2] = 1;
        xc[3] = 0;
    }

    private void ryc(Integer[] yc) {
        yc[0] = 0;
        yc[1] = -1;
        yc[2] = 0;
        yc[3] = 1;
    }


    // Random Order Search
    private void ROS(Room[][] rooms, Room[][] visits, int x, int y) {
        List<pt> pts = new ArrayList<>();
        pts.add(new pt(x, y));
        int at = 0;
        Boolean[] stat = new Boolean[4];
        Integer[] xc = new Integer[4];
        Integer[] yc = new Integer[4];
        Random r = new Random();

        while (!pts.isEmpty()) {
            int visitable = mkstat(stat, visits, pts.get(at).x, pts.get(at).y);

            if (visitable == 0)
                pts.remove(at);
            else {
                rxc(xc);
                ryc(yc);
                shuffle(xc, yc, stat);
                shuffle(xc, yc, stat);
                shuffle(xc, yc, stat);

                int letsVisit = r.nextInt(1, visitable + 1);

                for (int i = 0, visited = 0; i < 4 && visited <= letsVisit; i++) {
                    if (stat[i]) {
                        visited++;
                        visits[pts.get(at).y + yc[i]][pts.get(at).x + xc[i]] = rooms[pts.get(at).y][pts.get(at).x];
                        rooms[pts.get(at).y][pts.get(at).x].configureDoors(this);

                        boolean undirected = r.nextBoolean();
                        selection.put(directions[i], new Door(rooms[pts.get(at).y + yc[i]][pts.get(at).x + xc[i]], true));
                        rooms[pts.get(at).y + yc[i]][pts.get(at).x + xc[i]].configureDoors(this);
                        selection.put(rdirections[i], new Door(rooms[pts.get(at).y][pts.get(at).x], undirected));

                        pts.add(new pt(pts.get(at).x + xc[i], pts.get(at).y + yc[i]));
                    }
                }
            }

            at = (int)(Math.random() * pts.size());
        }
    }

    // Random DFS
    private void RDFS(Room[][] rooms, Room[][] visits, int x, int y) {
        Boolean[] stat = new Boolean[] { false, false, false, false };
        Integer[] xc = new Integer[4];
        Integer[] yc = new Integer[4];
        int visitable =  mkstat(stat, visits, x, y);

        while (visitable > 0 ){
            rxc(xc);
            ryc(yc);
            shuffle(stat, xc, yc);
            shuffle(xc, yc, stat);
            shuffle(xc, yc, stat);

            for (int i = 0; i < 4; i++)
                if (stat[i]) {
                    visits[y + yc[i]][x + xc[i]] = rooms[y][x];

                    rooms[y][x].configureDoors(this);

                    boolean undirected = new Random().nextBoolean();
                    selection.put(directions[i], new Door(rooms[y + yc[i]][x + xc[i]], true));
                    rooms[y + yc[i]][x + xc[i]].configureDoors(this);
                    selection.put(rdirections[i], new Door(rooms[y][x], undirected));

                    RDFS(rooms, visits, x + xc[i], y + yc[i]);
                    break;
                }

            visitable =  mkstat(stat, visits, x, y);
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
    private HashMap<Direction, Door> selection;
    public void acceptDoors(HashMap<Direction, Door> doors) {
        selection = doors;
    }
    private void cyclify(Room[][] rooms, Room[][] visits) {
        Random r = new Random();
        Boolean[] stat = new Boolean[4];
        Integer[] xc = new Integer[4];
        Integer[] yc = new Integer[4];
        rxc(xc);
        ryc(yc);

        for (int i = 0; i < rooms.length; i++)
            for (int j = 0; j < rooms[0].length; j++) {
                mkstat2(stat, rooms, visits, i, j);

                for (int k = 0; k < 4; k++)
                    if (stat[k]) {
                        if (r.nextDouble(1, 11) > 6.5) {
                            boolean b1 = r.nextInt(0, 2) == 0;
                            rooms[i + yc[k]][j + xc[k]].configureDoors(this);
                            selection.put(directions[k], new Door(rooms[i][j], b1));
                            rooms[i][j].configureDoors(this);
                            selection.put(rdirections[k], new Door(rooms[i + yc[k]][j + xc[k]], !b1));
                        }
                    }
            }
    }

    private void generate() {
        Room[][] rooms = new Room[height][width];

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                rooms[i][j] = new Room();

        Room[][] visits = new Room[height][width];
        Random r = new Random();
        int ry = r.nextInt(0, height);
        int rx = r.nextInt(0, width);
        visits[ry][rx] = rooms[ry][rx];
        ROS(rooms, visits, rx, ry);
        cyclify(rooms, visits);
    }

    public void remember(Room r) {

    }

    public void forget(Room room) {
        rooms.remove(room);
    }

    private void update() {
        logger.fine("Labyrinth is changing.");
    }

    private int height, width;
    public Labyrinth(int width, int height) {
        this.height = height;
        this.width = width;
        generate();
        logger.fine("Labyrinth created");
    }

    /** calls the update on every object
     * and asks players to move (later)
     *
     */
    @Override
    public void step() {
        logger.fine("Labyrinth step");
        update();
    }
}
