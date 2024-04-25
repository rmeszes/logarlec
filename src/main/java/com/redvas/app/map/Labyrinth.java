package com.redvas.app.map;

import com.redvas.app.App;
import com.redvas.app.Game;
import com.redvas.app.Steppable;
import com.redvas.app.players.Player;
import com.redvas.app.players.Undergraduate;

import java.util.*;
import java.util.logging.Logger;



public class Labyrinth implements Steppable {
    private Game game;
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

    private <T, E, J> void shuffle(T[] array1, E[] array2, J[] array3) {

        for (int i = 0; i < array1.length - 1; i++) {
            int rid = random.nextInt(i, array1.length);

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
    private void randomOrderSearch(Room[][] rooms, Room[][] visits, int x, int y) {
        List<PT> pts = new ArrayList<>();
        pts.add(new PT(x, y));
        int at = 0;
        Boolean[] stat = new Boolean[4];
        Integer[] xc = new Integer[4];
        Integer[] yc = new Integer[4];
        Random r = random;

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
                    if (stat[i] != null && stat[i]) {
                        visited++;
                        visits[pts.get(at).y + yc[i]][pts.get(at).x + xc[i]] = rooms[pts.get(at).y][pts.get(at).x];
                        rooms[pts.get(at).y][pts.get(at).x].configureDoors(this);

                        boolean undirected = r.nextBoolean();
                        selection.put(directions[i], new Door(rooms[pts.get(at).y + yc[i]][pts.get(at).x + xc[i]], true));
                        rooms[pts.get(at).y + yc[i]][pts.get(at).x + xc[i]].configureDoors(this);
                        selection.put(rdirections[i], new Door(rooms[pts.get(at).y][pts.get(at).x], undirected));

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
        Integer[] xc = new Integer[4];
        Integer[] yc = new Integer[4];
        int visitable =  mkstat(stat, visits, x, y);

        while (visitable > 0 ){
            rxc(xc);
            ryc(yc);
            shuffle(stat, xc, yc);
            shuffle(xc, yc, stat);
            shuffle(xc, yc, stat);

            for (int i = 0; i < 4; i++) {
                if (stat[i] != null && stat[i]) {
                    visits[y + yc[i]][x + xc[i]] = rooms[y][x];

                    rooms[y][x].configureDoors(this);

                    boolean undirected = random.nextBoolean();
                    selection.put(directions[i], new Door(rooms[y + yc[i]][x + xc[i]], true));
                    rooms[y + yc[i]][x + xc[i]].configureDoors(this);
                    selection.put(rdirections[i], new Door(rooms[y][x], undirected));

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
        Integer[] xc = new Integer[4];
        Integer[] yc = new Integer[4];
        rxc(xc);
        ryc(yc);

        for (int i = 0; i < rooms.length; i++)
            for (int j = 0; j < rooms[0].length; j++) {
                mkstat2(stat, rooms, visits, i, j);

                for (int k = 0; k < 4; k++) {
                    if (stat[k] != null && stat[k] && (r.nextDouble(1, 11) > 6.5)) {
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
        Room[][] roomsLocal = new Room[height][width];

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                roomsLocal[i][j] = new Room();

        Room[][] visits = new Room[height][width];
        Random r = random;
        int ry = r.nextInt(0, height);
        int rx = r.nextInt(0, width);
        visits[ry][rx] = roomsLocal[ry][rx];
        randomOrderSearch(roomsLocal, visits, rx, ry);
        //cyclify(roomsLocal, visits);
    }

    public void remember(Room r) {
        //TODO gondolom
    }

    public void forget(Room room) {
        rooms.remove(room);
    }

    private void update() {
        logger.fine("Labyrinth is changing.");
    }

    private final int height;
    private final int width;
    public Labyrinth(int width, int height, Game game) {
        if(height < 1) height = 1;
        if(width < 1) width = 1;
        this.height = height;
        this.width = width;
        generate();
        logger.fine("Labyrinth created");
        this.game = game;
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
}
