package com.redvas.app.players;

import com.redvas.app.App;
import com.redvas.app.Game;
import com.redvas.app.items.RottenCamembert;
import com.redvas.app.map.Direction;
import com.redvas.app.map.Room;
import com.redvas.app.Steppable;
import com.redvas.app.items.Item;

import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

//absztrakt class, majd az implementációk lesznek tesztelve
public abstract class Player implements Steppable {
    private final String name;
    private Room where;
    private ArrayList<Item> items;
    private int faintCountdown;
    protected Game game;
    public String getName() { return name; }
    // protected Game getGame() { return new Game(); } TODO
    protected static final Logger logger = Logger.getLogger("Player");

    public Player(String name) {
        this.name = name;
    }
    /**
     *
     * @param rounds: number of rounds until they are protected
     */
    public void setProtectionFor(int rounds) {
        logger.fine(() -> this + " gained protection from being dropped out for " + rounds + " rounds");
    }

    /**
     *
     * @param index: chosen item that they want to pick
     * @return: item that they picked
     */
    protected Item getItem(int index) {
        return new RottenCamembert();
    }

    /** this item behaves differently than others
     *
     */
    public abstract void pickLogarlec();

    static {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINEST);
        logger.addHandler(handler);
        logger.setLevel(Level.FINEST);
    }

    /** drops the items from inventory
     *
     */
    public void faint() {
        System.out.print("Does the player have FFP2 mask on? (y/n)");

        if(!App.reader.nextLine().equals("y")) {
            logger.fine(() -> this + " fainted");
        }
    }

    /** currently moving player
     *
     */
    public abstract void step();// Ez absztrakt a modell szerint

    /**
     *
     * @param item: picked item that they will dispose of
     */
    public void removeFromInventory(Item item) {
        logger.fine(() -> this + " no longer holds this " + item);
    }

    /**
     *
     * @param item: picked item that they will pick up
     */
    public void addToInventory(Item item) {
        logger.fine(() -> this + " acquired a(n) " + item);
    }

    /** only profs
     *
     */
    public abstract void paralyze();        // Ennek dőltnek kellene lennie a modellen?

    /** only undergrads
     *
     */
    public abstract void dropout();         // Ennek dőltnek kell lennie a modellen?

    /**
     *
     * @param location: room where they are
     */
    private void setWhere(Room location) {
        logger.fine(this + " reset its location");
    }   // Ez hiányzik a modellből

    /**
     *
     * @return room: identifier of currently occupied room (by this player)
     */
    public Room where() {
        return new Room();
    }

    /**
     *
     * @param room: chosen room where they move
     */
    public void moveTo(Room room) {
        logger.fine(() -> this + " commences its relocation");
        setWhere(room);
    }

    /** player chose to activate this protection
     *
     */
    public void useFFP2() {
        logger.fine(() -> this + " began using an FFP2 mask");
    }

    /**
     *
     * @param index: identifier of item they want to pick UP
     */
    private void pickItem(int index) {
        logger.fine(() -> this + " chose to pick up " + where().getItem(index));
        where().getItem(index).pickup(this);
    }

    /**
     *
     * @param index: identifier of item they want to put down
     */
    private void disposeItem(int index) {
        logger.fine(() -> this + " chose to dispose of " + getItem(index));
        getItem(index).dispose();
    }

    @Override
    public abstract String toString();      // Ez gondolom csak a skeletonhoz kellett

    private void moveTowards (Direction direction) {}
    public void dropItems() {}
    public void shceduleDrop() {}
    private void consoleAct() {}
    private void consoleMove() {}
    private void consoleMoveTowards(Direction direction) {}
    protected abstract boolean useItem(int index);
}
