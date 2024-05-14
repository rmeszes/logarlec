package com.redvas.app.map.rooms;

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
    private final int id;
    public int getID() {
        return id;
    }

    public int getCapacity() { return capacity; }

    public EnchantedRoom convertToEnchanted(int capacity) {

        EnchantedRoom er = new EnchantedRoom(labyrinth, id, capacity);

        for (Map.Entry<Direction, Door> e : doors.entrySet()) {
            e.getValue().setConnection(e.getKey().getReverse(), er);
            e.getValue().setVanished(Math.random() > 0.5);
        }

        er.doors = doors;
        return er;
    }

    public ResizingRoom convertToResizing(Direction expandDirection, int capacity) {
        ResizingRoom rr = new ResizingRoom(id, labyrinth, capacity, expandDirection);

        for (Map.Entry<Direction, Door> e : doors.entrySet())
            e.getValue().setConnection(e.getKey().getReverse(), rr);

        rr.doors = doors;
        return rr;
    }

    public List<Item> getItems() { return items; }

    public Element saveXML(Document document) {
        Element room = document.createElement("room");
        room.setAttribute("capacity", String.valueOf(capacity));
        room.setAttribute("type", this.getClass().getName());
        room.setAttribute("id", String.valueOf(id));
        room.setAttribute("stickiness", String.valueOf(stickiness));
        Element occupantsXML = document.createElement("occupants");

        for (Player p : this.occupants)
            occupantsXML.appendChild(p.saveXML(document));

        Element listenersXML = document.createElement("phantom_listeners");

        for (ProximityListener pl : this.listeners) {
            Element e;

            if ((e = pl.savePhantomListenerXML(document)) != null)
                listenersXML.appendChild(e);
        }

        Element itemsXML = document.createElement("items");

        for (Item i : this.items)
            itemsXML.appendChild(i.saveXML(document));

        Element doorsXML = document.createElement("doors");

        for (Map.Entry<Direction, Door> d : doors.entrySet()) {
            Element door = document.createElement("door");
            door.setAttribute("is_passable", String.valueOf(d.getValue().isPassable(d.getKey())));
            door.setAttribute("connects_to", String.valueOf(d.getValue().connectsTo(d.getKey()).getID()));
            door.setAttribute("is_vanished", String.valueOf(d.getValue().isVanished()));
            doorsXML.appendChild(door);
        }

        room.appendChild(doorsXML);
        room.appendChild(itemsXML);
        room.appendChild(occupantsXML);
        return room;
    }

    
    private static final Logger logger = App.getConsoleLogger(Room.class.getName());
    protected final Labyrinth labyrinth;
    public void configureDoors(Door d) {
        sendDoors(d);
    }
    public void sendDoors(Door d) {
        d.acceptDoors(doors);
    }
    private final List<ProximityListener> listeners = new ArrayList<>();
    private final List<Item> items = new ArrayList<>();
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

    public void loadXML(Element room) {
        stickiness = Integer.parseInt(room.getAttribute("stickiness"));
    }

    public Room(Labyrinth labyrinth, Integer id, Integer capacity) {
        this.labyrinth = labyrinth;
        this.id = id;
        this.capacity = capacity;
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

    protected static HashMap<Direction, Direction> reverseDirections = new HashMap<>();

    static {
        reverseDirections.put(Direction.UP, Direction.DOWN);
        reverseDirections.put(Direction.DOWN, Direction.UP);
        reverseDirections.put(Direction.LEFT, Direction.RIGHT);
        reverseDirections.put(Direction.RIGHT, Direction.LEFT);
    }

    public List<Player> getOccupants() { return occupants; }
    protected List<Player> occupants = new ArrayList<>();
    protected HashMap<Direction, Door> doors = new HashMap<>(8);

    private final int capacity;

    // to be called before addOccupant()
    public void subscribeToProximity(ProximityListener pl) {
        listeners.forEach(pl::affect);

        if(listeners.isEmpty()) listeners.add(pl);

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

        if ((door = doors.getOrDefault(d, null)) != null && (door.isPassable(d) && !door.isVanished()))
                return door.connectsTo(d);

        return null;
    }

    public List<Room> getAccessibleRooms() {
        return new ArrayList<>();
    }

    public Set<Direction> getAccessibleDirections() {
        HashSet<Direction> directions = new HashSet<>();
        for(Map.Entry<Direction,Door> entry : doors.entrySet()) {
            if(entry.getValue().isPassable(entry.getKey()) && !entry.getValue().isVanished()) directions.add(entry.getKey());
        }
        return directions;
    }
}
