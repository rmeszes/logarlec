package com.redvas.app.map.Rooms;

import com.redvas.app.App;
import com.redvas.app.Steppable;
import com.redvas.app.items.Item;
import com.redvas.app.map.Direction;
import com.redvas.app.map.Door;
import com.redvas.app.map.Labyrinth;
import com.redvas.app.players.Player;
import com.redvas.app.players.ProximityListener;

import java.util.*;
import java.util.logging.Logger;

public class Room implements Steppable {
    private static final Logger logger = App.getConsoleLogger(Room.class.getName());
    public void configureDoors() {
        labyrinth.acceptDoors(doors);
    }
    private final List<ProximityListener> listeners = new ArrayList<>();
    private final ArrayList<Item> items = new ArrayList<>();
    protected final Labyrinth labyrinth;
    private int stickiness = 0;
    protected boolean incorporatable() {
        return true;
    }

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

    public void receiveDoors() {
        doors = labyrinth.sendDoors();
    }

    protected static HashMap<Direction, Direction> reverseDirections = new HashMap<>();


    static {
        reverseDirections.put(Direction.UP, Direction.DOWN);
        reverseDirections.put(Direction.DOWN, Direction.UP);
        reverseDirections.put(Direction.LEFT, Direction.RIGHT);
        reverseDirections.put(Direction.RIGHT, Direction.LEFT);
    }

    protected List<Player> occupants = new ArrayList<>();
    protected Map<Direction, Door> doors = new HashMap<>();
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
