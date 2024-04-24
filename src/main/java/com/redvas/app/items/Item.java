package com.redvas.app.items;

import com.redvas.app.map.Room;
import com.redvas.app.players.Player;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Item {
    protected Player owner;
    protected Room whichRoom;
    protected String name;
    protected boolean isReal;

    public Room where() {
        if (owner != null) return owner.where();
        else return whichRoom;
    }
    /**
     *
     * @return identificator of room (later)
     */

    /** the Item was destroyed/used up, it no longer exists
     *
     */
    protected void destroy() {
        owner().removeFromInventory(this);
        logger.fine(() -> this + " was garbage collected (destroyed)");
    }

    /**
     *
     * @return Undergrad: who owns the item
     */
    public Player owner() {
        return owner;
    }

    /**
     *
     * @param player: the one that is going to own it
     */

    protected static final Logger logger = Logger.getLogger("Item");

    static {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINEST);
        logger.addHandler(handler);
        logger.setLevel(Level.FINEST);
    }

    /** each item implements it differently
     *
     */
    public void use() {
        logger.fine(() -> this + " can not be used");
    }

    /** item was put on the floor (removed from inventory, added to floor of room)
     *
     */
    public void dispose() {
        logger.fine(() -> this + " is being disposed of");
        whichRoom = owner.where();
        owner().removeFromInventory(this);
        owner().where().addItem(this);
    }

    /**
     *
     * @param who: player that will pick up
     */
    public void pickup(Player who) {
        logger.fine(() -> this + " is being picked up by " + who);
        owner = who;
        who.addToInventory(this);
        whichRoom.removeItem(this);
        whichRoom = null;
    }

    /**
     *
     * @return Az Item neve
     */
    @Override
    public abstract String toString();
    public void merge(Transistor item) { logger.fine(() -> this + " can not be merged"); }
}
