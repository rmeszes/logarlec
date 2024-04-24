package com.redvas.app.map;

import com.redvas.app.App;
import com.redvas.app.Steppable;
import com.redvas.app.items.Item;
import com.redvas.app.items.RottenCamembert;
import com.redvas.app.players.Player;
import com.redvas.app.players.ProximityListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Room implements Steppable {
    private List<ProximityListener> listeners = new ArrayList<>();
    private ArrayList<Item> items;
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
    public Item getItem(int index) { return new RottenCamembert(); }
    public List<Item> getItems() { return items; }

    protected static final Logger logger = Logger.getLogger("Item");

    static {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINEST);
        logger.addHandler(handler);
        logger.setLevel(Level.FINEST);
    }

    public Room() {
        logger.fine("Room init");
    }

    /**
     *
     * @return list: players inside
     */
    /**
     *
     * @param player: the one that left the room
     */
    public void removeOccupant(Player player) {
        logger.fine(()->"Room occupant list no longer contains this " + player);
    }

    /**
     *
     * @param player: the one that stepped inside
     */
    public void addOccupant(Player player) {
        logger.fine(()->"Room occupant list now contains " + player);
    }

    /** undergrad has lost the game
     *
     */

    /**
     *
     * @return bool: is there space in the room
     */

    /**
     *
     * @param targetRoom: where player wants to move
     * @return bool: whether it is neighboring or not
     */
    private Boolean isAccessible(Room targetRoom) { return new Door().isPassable(); }

    /** initialization or someone opened a Camembert
     *
     */
    /** if there are undergrads, they lose the game
     *
     */

    /** only profs
     *
     */

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

    public void subscribeToProximity(ProximityListener pl) {

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

        for (ProximityListener pl : listeners)
            pl.proximityEndOfRound(occupants);
    }

    /**
     *
     * @param other: the room it will be merged with
     */
    public void mergeWithRoom(Room other) {
        logger.fine("Setting this room's attributes to new ones..");
        other.destroy();
        //labyrinth.update()
    }

    /**
     * After the room is merged it has to disappear
     */
    public void destroy() {
        logger.fine("This room got destroyed");
    }

    /**
     *
     * @return Room: new room if it divided
     */
    public Room divide() {
        System.out.print("Does this room has less than 4 neighbouring rooms? (y/n)");

        if(App.reader.nextLine().equals("y")) {
            logger.fine("Splitting into another room..");
            return new Room();
            //lab.update()
        } else {
            logger.fine("The room cannot split, it has no space");
            return null;
        }
    }

    public Room isAccessible(Direction d) {
        Door door = null;

        if ((door = doors.getOrDefault(d, null)) != null)
            if (door.isPassable() && !door.isVanished())
                return door.connectsTo();

        return null;
    }
}
