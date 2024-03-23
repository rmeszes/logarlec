package com.redvas.app.players;

import com.redvas.app.App;
import com.redvas.app.Game;
import com.redvas.app.items.RottenCamembert;
import com.redvas.app.map.Room;
import com.redvas.app.Steppable;
import com.redvas.app.items.Item;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

//absztrakt class, majd az implementációk lesznek tesztelve
public abstract class Player implements Steppable {
    private String getName() { return ""; }
    protected Game getGame() { return new Game(); }
    protected static final Logger logger = Logger.getLogger("Player");

    public void setProtectionFor(int rounds) {
        logger.fine(this + " gained protection from being dropped out for " + rounds + " rounds");
    }

    protected Item getItem(int index) { return new RottenCamembert(); }

    public abstract void pickLogarlec();

    static {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINEST);
        logger.addHandler(handler);
        logger.setLevel(Level.FINEST);
    }
    public void faint() {
        logger.fine(this + " fainted");
    }

    public void step() {
        logger.fine(this + " is on his/her turn");
    }

    public void removeFromInventory(Item item) {
        logger.fine(this + " was confiscated of a(n) " + item);
    }

    public void addToInventory(Item item) {
        logger.fine(this + " acquired a(n) " + item);
    }

    public abstract void paralyze();

    public abstract void dropout();

    private void setWhere(Room location) {
        logger.fine(this + " reset its location");
    }

    public Room where() {
        System.out.print(this + " asks for its position");
        String input = App.reader.nextLine();
        logger.fine(this + " is at: " + input);
        return new Room();
    }

    public void moveTo(Room room) {
        logger.fine(this + " commences its relocation");
        setWhere(room);
    }

    public void useFFP2() {
        logger.fine(this + " began using an FFP2 mask");
    }

    protected void pickItem(int index) {
        logger.fine(this + " chose to pick up " + where().getItem(index));
        where().getItem(index).pickup(this);
    }

    protected abstract void useItem(int index);

    protected void disposeItem(int index) {
        logger.fine(this + " chose to dispose of " + getItem(index));
        getItem(index).dispose();
    }

    @Override
    public abstract String toString();
}
