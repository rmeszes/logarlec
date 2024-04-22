package com.redvas.app.players;

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
    // tagváltozók
    private Room where;
    private ArrayList<Item> items;
    private int faintCountdown;
    protected final Game game;        // akár ez is lehet final

    protected static final Logger logger = Logger.getLogger("Player");

    // konstruktor
    public Player(Room room, Game game) {
        //this.name = name;
        this.where = room;
        this.items = new ArrayList<Item>();
        this.faintCountdown = 0;
        this.game = game;
    }
    /**
     *
     * @param rounds: number of rounds until they are protected
     */
    public void setProtectionFor(int rounds) {

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

    }

    /**
     *
     * @param item: picked item that they will pick up
     */
    public void addToInventory(Item item) {

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
     * @param room: chosen room where they move
     */
    public void moveTo(Room room) {

    }

    /** player chose to activate this protection
     *
     */
    public void useFFP2() {

    }

    /**
     *
     * @param index: identifier of item they want to pick UP
     */
    private void pickItem(int index) {

    }

    /**
     *
     * @param index: identifier of item they want to put down
     */
    private void disposeItem(int index) {

    }

    private void moveTowards (Direction direction) {}
    public void dropItems() {}
    public void shceduleDrop() {}
    private void consoleAct() {}
    private void consoleMove() {}
    private void consoleMoveTowards(Direction direction) {}
    protected abstract boolean useItem(int index);


    // getters and setters
    /**
     *
     * @return room: identifier of currently occupied room (by this player)
     */
    public Room getWhere() { return new Room(); }       // ezt refaktoráltam where -> getWhere
    public ArrayList<Item> getItems() { return items; }     // ehhez setter nem kell
    public int getFaintCountdown() { return faintCountdown; }       // ehhez sem kell setter
    public Game getGame() { return game; }        // ez protected volt (miert?)

    /**
     *
     * @param location: room where they are
     */
    private void setWhere(Room location) {

    }   // Ez hiányzik a modellből




    @Override
    public abstract String toString();      // Ez gondolom csak a skeletonhoz kellett
}
