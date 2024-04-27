package com.redvas.app.items;

import com.redvas.app.App;
import com.redvas.app.map.Rooms.Room;
import com.redvas.app.players.Player;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.management.StringValueExp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public abstract class Item {
    protected Player owner;
    protected Room whichRoom = null;
    protected String name;
    protected boolean isReal;

    private final int id;

    public void loadXML(Element item, HashMap<Integer, Item> id2item) {
        isReal = Boolean.parseBoolean(item.getAttribute("is_real"));
    }

    protected Item(int id, Player owner) {
        this.owner = owner;
        this.owner.addToInventory(this);
        this.id = id;
    }

    protected Item(Integer id, Room whichRoom) {
        this.whichRoom = whichRoom;
        this.whichRoom.addItem(this);
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public Element saveXML(Document document) {
        Element item = document.createElement("item");
        item.setAttribute("id", String.valueOf(id));
        item.setAttribute("is_real", String.valueOf(isReal));
        item.setAttribute("type", this.getClass().getName());
        item.setAttribute("owner", owner == null ? "null" : String.valueOf(owner.getID()));
        item.setAttribute("whichRoom", whichRoom == null ? "null" : String.valueOf(whichRoom.getID()));
        return item;
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
