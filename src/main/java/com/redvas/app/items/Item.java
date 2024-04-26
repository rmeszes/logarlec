package com.redvas.app.items;

import com.redvas.app.App;
import com.redvas.app.map.Rooms.Room;
import com.redvas.app.players.Player;

import java.util.logging.Logger;

public abstract class Item {
    protected Player owner;
    protected Room whichRoom;
    protected String name;
    protected boolean isReal;

    protected Item(Room whichRoom) {
        this.whichRoom = whichRoom;
    }

    public Room getRoom() {
        if (owner != null)
            return owner.where();
        else
            return whichRoom;
    }

    /** the Item was destroyed/used up, it no longer exists
     *
     */
    protected void destroy() {
        getOwner().removeFromInventory(this);
        logger.fine(() -> this + " was garbage collected (destroyed)");
    }

    /**
     *
     * @return Undergrad: who owns the item
     */
    public Player getOwner() {
        return owner;
    }


    protected static final Logger logger = App.getConsoleLogger(Item.class.getName());



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
        getOwner().removeFromInventory(this);
        getOwner().where().addItem(this);
        owner = null;
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
