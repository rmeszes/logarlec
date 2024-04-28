package com.redvas.app.map.Rooms;

import com.redvas.app.App;
import com.redvas.app.Steppable;
import com.redvas.app.items.Item;
import com.redvas.app.map.Direction;
import com.redvas.app.map.Door;
import com.redvas.app.map.Labyrinth;
import com.redvas.app.players.Player;
import com.redvas.app.players.ProximityListener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.*;
import java.util.logging.Logger;

public class Room implements Steppable {
    private int id;
    public int getID() {
        return id;
    }

    public Element saveXML(Document document) {
        Element room = document.createElement("room");
        room.setAttribute("capacity", String.valueOf(capacity));
        room.setAttribute("type", this.getClass().getName());
        room.setAttribute("id", String.valueOf(id));
        Element doors = document.createElement("doors");
        room.appendChild(doors);

        for (Map.Entry<Direction, Door> e : this.doors.entrySet()) {
            Element door = document.createElement("door");
            door.setAttribute("direction", e.getKey().toString());
            door.setAttribute("connects_to", String.valueOf(e.getValue().connectsTo().getID()));
            door.setAttribute("id", String.valueOf(getID()));
            door.setAttribute("is_passable", String.valueOf(e.getValue().isPassable()));
            door.setAttribute("is_vanished", String.valueOf(e.getValue().isVanished()));
            doors.appendChild(door);
        }

        Element occupants = document.createElement("occupants");

        for (Player p : this.occupants)
            occupants.appendChild(p.saveXML(document));

        Element listeners = document.createElement("phantom_listeners");

        for (ProximityListener pl : this.listeners) {
            Element e;

            if ((e = pl.savePhantomListenerXML(document)) != null)
                listeners.appendChild(e);
        }

        Element items = document.createElement("items");

        for (Item i : this.items)
            items.appendChild(i.saveXML(document));

        room.appendChild(items);
        room.appendChild(occupants);
        return room;
    }
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
        logger.finest(()->"Room item inventory no longer holds this " + item);
    }

    /**
     *
     * @param index: type of item
     * @return something, it will probably be random
     */
    public Item getItem(int index) {
        return items.get(index);
    }

    public void loadXML(Element room) {}

    private int n = 0;

    public Room(Labyrinth labyrinth, Integer id) {
        this.labyrinth = labyrinth;
        this.id = id;
    }
    /**
     *
     * @param player: the one that left the room
     */
    public void removeOccupant(Player player) {
        logger.finest(()->"Room occupant list no longer contains this " + player);
    }

    // to be called after subscribeToProximity()
    /**
     *
     * @param player: the one that stepped inside
     */
    public void addOccupant(Player player) {
        logger.finest(()->"Room occupant list now contains " + player);
        listeners.forEach(listener -> listener.proximityChanged(player));
        stickiness++;
        occupants.add(player);
    }



    /** initialization or someone put it down
     *
     * @param item: that was added to the room
     */
    public void addItem(Item item) {
        logger.finest(()->"Room item inventory was added to a(n) " + item);
        items.add(item);
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
        logger.finest("Room is on its turn");
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
