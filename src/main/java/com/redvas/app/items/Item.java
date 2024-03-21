package com.redvas.app.items;

import com.redvas.app.players.Player;
import com.redvas.app.players.Undergraduate;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Item {
    public Player owner() {
        return new Undergraduate();
    }

    private void setOwner(Player player) {
        logger.fine("Item owner regisztrálva ehhez az Item-hez");
    }

    protected static final Logger logger = Logger.getLogger("Item");

    static {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINEST);
        logger.addHandler(handler);
        logger.setLevel(Level.FINEST);
    }
    public void use() {
        logger.fine("Ez a tárgy nem használható");
    }
    public void dispose() {
        owner().removeFromInventory(this);
        owner().where().addItem(this);
    }
    public void pickup(Player who) {
        setOwner(who);
        who.addToInventory(this);
    }
    public void merge(Transistor item) { logger.fine("Ez a tárgy nem vonható össze másikkal"); }

}
