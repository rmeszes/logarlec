package com.redvas.app.items;

import com.redvas.app.Game;
import com.redvas.app.map.Room;
import com.redvas.app.players.Player;
import com.redvas.app.players.Undergraduate;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Item {
    protected Player owner;
    protected Room whichRoom;
    protected String name;
    protected boolean isReal;
    /**
     *
     * @return identificator of room (later)
     */
    public Room getWhichRoom() {
        return whichRoom;
    }       // ezt átneveztem where -> getWhichRoom

    public void setWhichRoom(Room whichRoom) {
            this.whichRoom = whichRoom;
    }

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
        return new Undergraduate("skeleton", new Room(), new Game());
    }

    /**
     *
     * @param player: the one that is going to own it
     */
    public void setOwner(Player player) {
        logger.fine(() -> this + " was registered to " + player);
    }

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
        owner().removeFromInventory(this);
        owner().getWhere().addItem(this);
    }

    /**
     *
     * @param who: player that will pick up
     */
    public void pickup(Player who) {
        logger.fine(() -> this + " is being picked up by " + who);
        setOwner(who);
        who.addToInventory(this);
        getWhichRoom().removeItem(this);
    }

    /**
     *
     * @return Az Item neve
     */
    @Override
    public abstract String toString();
    public void merge(Transistor item) { logger.fine(() -> this + " can not be merged"); }
}
