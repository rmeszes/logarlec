package com.redvas.app.items;

import com.redvas.app.App;
import com.redvas.app.map.rooms.Room;
import com.redvas.app.players.Player;
import com.redvas.app.ui.items.ItemChangeListener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

public abstract class Item {
    protected static Random rand = new Random();

    protected Player owner;
    protected Room whichRoom = null;
    protected String name;
    protected boolean isReal;
    protected boolean isInRoom;

    private final int id;

    /**
     * Loads this item
     * @param item item to load
     * @param id2item Needed for overrides
     */
    public void loadXML(Element item, Map<Integer, Item> id2item) {
        isReal = Boolean.parseBoolean(item.getAttribute("is_real"));
    }

    protected Item(int id, Player owner) {
        this.owner = owner;
        this.owner.addToInventory(this);
        this.id = id;
        this.isInRoom = false;
        isReal = rand.nextBoolean();
    }

    protected Item(Integer id, Room whichRoom, Boolean isListener) {
        this.whichRoom = whichRoom;
        this.isInRoom = true;

        if (Boolean.FALSE.equals(isListener))
            this.whichRoom.addItem(this);

        isReal = rand.nextBoolean();
        this.id = id;

    }

    public void setIfReal(boolean b) {isReal = b;}
    public boolean isReal() {return isReal;}

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
        if (isInRoom == false)
            return owner.where();
        else
            return whichRoom;
    }

    /** the Item was destroyed/used up, it no longer exists
     *
     */
    protected void destroy() {
        getOwner().removeFromInventory(this);
        logger.fine(() -> this + " was taken from inventory");
        isInRoom = true; // owner = null -t helyettesíti, lehet nem is kell de idk
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
    public abstract void use();

    private ItemChangeListener listener = null;
    public void setListener(ItemChangeListener listener) {
        this.listener = listener;
    }

    /** item was put on the floor (removed from inventory, added to floor of room)
     *
     */
    public void dispose() {
        logger.fine(() -> this + " is being disposed of");
        this.whichRoom = owner.where();
        getOwner().removeFromInventory(this);
        getOwner().where().addItem(this);
        isInRoom = true;

        if (listener != null)
            listener.isInRoom(true);
    }

    /**
     *
     * @param who: player that will pick up
     */
    public void pickup(Player who) {
        if(who.getItems().size() < 5) {
            logger.fine(() -> this + " is being picked up by " + who);
            owner = who;
            who.addToInventory(this);
            whichRoom.removeItem(this);
            this.isInRoom = false;
            if (listener != null)
                listener.isInRoom(false);
        }
        else{
            logger.fine("Inventory full");
        }
    }

    /**
     *
     * @return Az Item neve
     */
    @Override
    public abstract String toString();

    /**
     *
     * @param item Needed for override
     */
    public void merge(Transistor item) { logger.fine(() -> this + " can not be merged"); }
}
