package com.redvas.app.map;

import com.redvas.app.App;
import com.redvas.app.Steppable;
import com.redvas.app.items.Item;
import com.redvas.app.players.Player;
import com.redvas.app.players.ProximityListener;

import java.util.*;
import java.util.logging.Logger;

public class Room implements Steppable {
    private static final Logger logger = App.getConsoleLogger(Room.class.getName());

    public void configureDoors(Labyrinth l) {
        l.acceptDoors(doors);
    }
    private final List<ProximityListener> listeners = new ArrayList<>();
    private final ArrayList<Item> items = new ArrayList<>();
    private final Labyrinth labyrinth;
    private int stickiness = 0;
    private boolean isMerged = false;
    /**
     *
     * @param item: someone picked it up
     */
    public void removeItem(Item item) {
        logger.fine(()->"Room item inventory no longer holds this " + item);
    }

    /**
     *
     * @param index: type of item
     * @return something, it will probably be random
     */
    public Item getItem(int index) {
        return items.get(index);
    }

    private int n = 0;

    public Room(Labyrinth labyrinth) {
        this.labyrinth = labyrinth;
    }
    /**
     *
     * @param player: the one that left the room
     */
    public void removeOccupant(Player player) {
        logger.fine(()->"Room occupant list no longer contains this " + player);
    }

    // to be called after subscribeToProximity()
    /**
     *
     * @param player: the one that stepped inside
     */
    public void addOccupant(Player player) {
        logger.fine(()->"Room occupant list now contains " + player);
        listeners.forEach(listener -> listener.proximityChanged(player));
        stickiness++;
    }



    /** initialization or someone put it down
     *
     * @param item: that was added to the room
     */
    public void addItem(Item item) {
        logger.fine(()->"Room item inventory was added to a(n) " + item);
    }

    private List<Player> occupants = new ArrayList<>();
    private HashMap<Direction, Door> doors = new HashMap<>();
    private int capacity;

    // to be called before addOccupant()
    public void subscribeToProximity(ProximityListener pl) {
        listeners.forEach(pl::affect);

        for (int i = 0; i < listeners.size(); i++)
            if (pl.listenerPriority() > listeners.get(i).listenerPriority()){
                listeners.add(i, pl);
                return;
            }

        pl.proximityInitially(occupants);
    }

    public void unsubscribeFromProximity(ProximityListener pl) {
        listeners.remove(pl);
    }

    public Boolean canAccept() {
        return occupants.size() < capacity;
    }

    // Ez azert van itt, hogy olyan esetekben amikor valakin maszk van, nem ajul el, hanem csak akkro amikor mar bent van es lejar majd a protekcio
    // A dropout eseten hasonlo az erveles, mert a TVSZ/HolyBeer lejaratat is figyelembe kell venni

    /** for special cases, checking the protection of undergrads
     *
     */
    @Override
    public void step() {
        logger.fine("Room is on its turn");
        listeners.forEach(listener -> listener.proximityEndOfRound(occupants));
    }

    private Direction getNeighborDirection(Room r) {
        Optional<Direction> d = doors
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().connectsTo() == r)
                .map(Map.Entry::getKey)
                .findFirst();

        return d.orElse(null);
    }

    public void split() {
        //TODO if (!isMerged) return;
        if (!isMerged) return;

        Room r = new Room(labyrinth);
        labyrinth.remember(r);

        if (doors.getOrDefault(Direction.RIGHT, null) == null) {
            // Vertical alignment
            // Let's newly created room be on TOP
            r.doors.put(Direction.LEFT, doors.get(Direction.TOP_LEFT));
            r.doors.put(Direction.RIGHT, doors.get(Direction.TOP_LEFT));
            r.doors.put(Direction.UP, doors.get(Direction.UP));
            doors.remove(Direction.TOP_LEFT);
            doors.remove(Direction.BOTTOM_RIGHT);

            boolean b = new Random().nextBoolean();
            doors.put(Direction.UP, new Door(r, b));
            r.doors.put(Direction.DOWN, new Door(this, !b));

            doors.put(Direction.LEFT, doors.get(Direction.BOTTOM_LEFT));
            doors.put(Direction.RIGHT, doors.get(Direction.BOTTOM_RIGHT));
            doors.remove(Direction.BOTTOM_LEFT);
            doors.remove(Direction.BOTTOM_RIGHT);
        }
        else {
            // Horizontal alignment
            // Let's newly created room be to the LEFT
            r.doors.put(Direction.LEFT, doors.get(Direction.LEFT));
            r.doors.put(Direction.UP, doors.get(Direction.TOP_LEFT));
            r.doors.put(Direction.DOWN, doors.get(Direction.BOTTOM_LEFT));
            doors.remove(Direction.TOP_LEFT);
            doors.remove(Direction.BOTTOM_LEFT);

            doors.put(Direction.UP, doors.get(Direction.TOP_RIGHT));
            doors.put(Direction.DOWN, doors.get(Direction.BOTTOM_RIGHT));
            doors.remove(Direction.TOP_RIGHT);
            doors.remove(Direction.BOTTOM_RIGHT);

            boolean b = new Random().nextBoolean();
            doors.put(Direction.LEFT, new Door(r, b));
            r.doors.put(Direction.RIGHT, new Door(this, !b));
        }
    }

    public void expandInDirection(Direction dir) {
        logger.fine("Setting this room's attributes to new ones..");

        Door door = null;

        if ((door = doors.getOrDefault(dir, null)) == null) return;

        Room room = door.connectsTo();

        if (isMerged || room.isMerged) return;

        if (dir == Direction.UP) {
            doors.put(Direction.TOP_LEFT, room.doors.get(Direction.LEFT));

            Door d;

            if ((d = room.doors.get(Direction.LEFT)) != null)
                d.connectsTo().doors.get(Direction.RIGHT).setConnection(this);

            doors.put(Direction.TOP_RIGHT, room.doors.get(Direction.RIGHT));

            if ((d = room.doors.get(Direction.RIGHT)) != null)
                d.connectsTo().doors.get(Direction.LEFT).setConnection(this);

            doors.put(Direction.BOTTOM_LEFT, doors.get(Direction.LEFT));
            doors.put(Direction.BOTTOM_RIGHT, doors.get(Direction.RIGHT));

            doors.put(Direction.LEFT, null);
            doors.put(Direction.RIGHT, null);

            doors.put(Direction.UP, room.doors.get(Direction.UP));

            if ((d = room.doors.get(Direction.UP)) != null)
                d.connectsTo().doors.get(Direction.DOWN).setConnection(this);

        }
        else if (dir == Direction.LEFT) {
            doors.put(Direction.TOP_LEFT, room.doors.get(Direction.UP));

            Door d;

            if ((d = room.doors.get(Direction.UP)) != null)
                d.connectsTo().doors.get(Direction.DOWN).setConnection(this);

            doors.put(Direction.TOP_RIGHT, doors.get(Direction.UP));
            doors.put(Direction.BOTTOM_LEFT, room.doors.get(Direction.DOWN));

            if ((d = room.doors.get(Direction.DOWN)) != null)
                d.connectsTo().doors.get(Direction.UP).setConnection(this);

            doors.put(Direction.BOTTOM_RIGHT, doors.get(Direction.DOWN));

            doors.put(Direction.LEFT, room.doors.get(Direction.LEFT));

            if ((d = room.doors.get(Direction.LEFT)) != null)
                d.connectsTo().doors.get(Direction.RIGHT).setConnection(this);


            doors.put(Direction.UP, null);
            doors.put(Direction.DOWN, null);
        }
        else if (dir == Direction.DOWN) {
            doors.put(Direction.TOP_LEFT, doors.get(Direction.LEFT));
            doors.put(Direction.TOP_RIGHT, doors.get(Direction.RIGHT));

            doors.put(Direction.BOTTOM_LEFT, room.doors.get(Direction.LEFT));

            Door d;

            if ((d = room.doors.get(Direction.LEFT)) != null)
                d.connectsTo().doors.get(Direction.RIGHT).setConnection(this);

            doors.put(Direction.BOTTOM_RIGHT, room.doors.get(Direction.RIGHT));

            if ((d = room.doors.get(Direction.RIGHT)) != null)
                d.connectsTo().doors.get(Direction.LEFT).setConnection(this);

            doors.put(Direction.LEFT, null);
            doors.put(Direction.RIGHT, null);

            doors.put(Direction.DOWN, room.doors.get(Direction.DOWN));

            if ((d = room.doors.get(Direction.DOWN)) != null)
                d.connectsTo().doors.get(Direction.UP).setConnection(this);
        }
        else if (dir == Direction.RIGHT) {
            doors.put(Direction.TOP_LEFT, doors.get(Direction.UP));
            doors.put(Direction.TOP_RIGHT, room.doors.get(Direction.UP));

            Door d;

            if ((d = room.doors.get(Direction.UP)) != null)
                d.connectsTo().doors.get(Direction.DOWN).setConnection(this);

            doors.put(Direction.BOTTOM_LEFT, doors.get(Direction.DOWN));
            doors.put(Direction.BOTTOM_RIGHT, room.doors.get(Direction.DOWN));

            if ((d = room.doors.get(Direction.DOWN)) != null)
                d.connectsTo().doors.get(Direction.UP).setConnection(this);

            doors.put(Direction.RIGHT, room.doors.get(Direction.RIGHT));

            if ((d = room.doors.get(Direction.RIGHT)) != null)
                d.connectsTo().doors.get(Direction.LEFT).setConnection(this);

            doors.put(Direction.UP, null);
            doors.put(Direction.DOWN, null);
        }

        room.destroy();
    }

    /**
     * After the room is merged it has to disappear
     */
    public void destroy() {
        logger.fine("This room got destroyed");
        labyrinth.forget(this);
    }

    /**
     *
     * @return Room: new room if it divided
     */

    public Room isAccessible(Direction d) {
        Door door = null;

        if ((door = doors.getOrDefault(d, null)) != null && (door.isPassable() && !door.isVanished()))
                return door.connectsTo();

        return null;
    }

    public List<Room> getAccessibleRooms() {
        return new ArrayList<>();
    }
}
