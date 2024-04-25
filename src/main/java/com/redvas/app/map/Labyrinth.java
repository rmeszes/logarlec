package com.redvas.app.map;

import com.redvas.app.App;
import com.redvas.app.Game;
import com.redvas.app.Steppable;
import com.redvas.app.players.Undergraduate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

            if (stat[i]) visitable++;
        }

        return visitable;
    }



    private void rdirs(Direction[] directions) {
        directions[0] = Direction.LEFT;
        directions[1] = Direction.UP;
        directions[2] = Direction.RIGHT;
        directions[3] = Direction.DOWN;
    }

    private void rrdirs(Direction[] rdirections) {
        directions[0] = Direction.RIGHT;
        directions[1] = Direction.DOWN;
        directions[2] = Direction.LEFT;
        directions[3] = Direction.UP;
    }

    private void reset() {
        rxc(xc);
        ryc(yc);
        rdirs(directions);
        rrdirs(rdirections);
    }

    Integer[] xc = new Integer[] { -1, 0, 1, 0 };
    Integer[] yc = new Integer[] { 0, -1, 0, 1 };

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
    private void randomOrderSearch(Room[][] rooms, Room[][] visits, int x, int y) {
        List<PT> pts = new ArrayList<>();
        pts.add(new PT(x, y));
        int at = 0;
        Boolean[] stat = new Boolean[4];
        Random r = random;

        while (!pts.isEmpty()) {
            int visitable = mkstat(stat, visits, pts.get(at).x, pts.get(at).y);

            if (visitable == 0)
                pts.remove(at);
            else {
                shuffle(xc, yc, stat, directions, rdirections);
                shuffle(xc, yc, stat, directions, rdirections);
                shuffle(xc, yc, stat, directions, rdirections);

                int letsVisit = r.nextInt(1, visitable + 1);

                for (int i = 0, visited = 0; i < 4 && visited <= letsVisit; i++) {
                    if (stat[i]) {
                        visited++;
                        visits[pts.get(at).y + yc[i]][pts.get(at).x + xc[i]] = rooms[pts.get(at).y][pts.get(at).x];
                        rooms[pts.get(at).y][pts.get(at).x].configureDoors(this);

                        selection.put(directions[i], new Door(rooms[pts.get(at).y + yc[i]][pts.get(at).x + xc[i]], true));
                        rooms[pts.get(at).y + yc[i]][pts.get(at).x + xc[i]].configureDoors(this);
                        selection.put(rdirections[i], new Door(rooms[pts.get(at).y][pts.get(at).x], true));

                        pts.add(new PT(pts.get(at).x + xc[i], pts.get(at).y + yc[i]));
                    }
                }
            }
            at = (int)Math.abs(random.nextGaussian());
            if(at >= pts.size() && !pts.isEmpty()) {
                at = pts.size() - 1;
            }
        }
    }

    // Random DFS
    private void randomDFS(Room[][] rooms, Room[][] visits, int x, int y) {
        Boolean[] stat = new Boolean[] { false, false, false, false };
        int visitable =  mkstat(stat, visits, x, y);

        while (visitable > 0 ){
            mkstat(stat, visits, x, y);
            shuffle(stat, xc, yc, directions, rdirections);
            shuffle(xc, yc, stat, directions, rdirections);
            shuffle(xc, yc, stat, directions, rdirections);

            for (int i = 0; i < 4; i++) {
                if (stat[i]) {
                    visits[y + yc[i]][x + xc[i]] = rooms[y][x];

                    rooms[y][x].configureDoors(this);

                    selection.put(directions[i], new Door(rooms[y + yc[i]][x + xc[i]], true));
                    rooms[y + yc[i]][x + xc[i]].configureDoors(this);
                    selection.put(rdirections[i], new Door(rooms[y][x], true));

                    randomDFS(rooms, visits, x + xc[i], y + yc[i]);
                    break;
                }
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
    private Map<Direction, Door> selection;
    public void acceptDoors(Map<Direction, Door> doors) {
        selection = doors;
    }
    private void cyclify(Room[][] rooms, Room[][] visits) {
        Random r = random;
        Boolean[] stat = new Boolean[4];

        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                mkstat2(stat, rooms, visits, x, y);

                for (int k = 0; k < 4; k++) {
                    if (stat[k] && r.nextDouble(0, 1) > 0.88) {
                        rooms[y][x].configureDoors(this);
                        selection.put(directions[k], new Door(rooms[y + yc[k]][x + xc[k]], true));
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
        randomOrderSearch(roomsLocal, visits, rx, ry);
        cyclify(roomsLocal, visits);

        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                rooms.add(roomsLocal[y][x]);
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

    public void emplacePlayers(String player1Name, String player2Name) {
        logger.fine("Placing players..");

        Room player1Place = rooms.get(random.nextInt(0,rooms.size()));
        Room player2Place = rooms.get(random.nextInt(0,rooms.size()));

        new Undergraduate(player1Name,player1Place, game);

        new Undergraduate(player2Name,player2Place, game);
    }

    //labirintus generálásnak lesznek még lépései (itemek, stb)
}
