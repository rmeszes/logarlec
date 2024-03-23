package com.redvas.app.items;

import com.redvas.app.map.Room;
import com.redvas.app.players.Player;
import com.redvas.app.players.Undergraduate;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Item {
    private Room where() { return new Room(); }
    protected void destroy() {
        owner().removeFromInventory(this);
        logger.fine(this + " was garbage collected (destroyed)");
    }

    public Player owner() {
        return new Undergraduate();
    }

    private void setOwner(Player player) {
        logger.fine(this + " was registered to " + player);
    }

    protected static final Logger logger = Logger.getLogger("Item");

    static {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINEST);
        logger.addHandler(handler);
        logger.setLevel(Level.FINEST);
    }

    public void use() {
        logger.fine(this + " can not be used");
    }
    public void dispose() {
        logger.fine(this + " is being disposed of");
        owner().removeFromInventory(this);
        owner().where().addItem(this);
    }
    public void pickup(Player who) {
        logger.fine(this + " is being picked up by " + who);
        setOwner(who);
        who.addToInventory(this);
        where().removeItem(this);
    }

    /**
     *
     * @return Az Item neve
     */
    @Override
    public abstract String toString();
    public void merge(Transistor item) { logger.fine(this + " can not be merged"); }
}
