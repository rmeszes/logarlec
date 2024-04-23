package com.redvas.app.players;

import com.redvas.app.Game;
import com.redvas.app.Steppable;
import com.redvas.app.items.Item;
import com.redvas.app.map.Direction;
import com.redvas.app.map.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

//absztrakt class, majd az implementációk lesznek tesztelve
public abstract class Player implements Steppable {
    // tagváltozók
    private Room where;
    private final ArrayList<Item> items;
    private int faintCountdown;     // unsigned int?
    protected final Game game;        // akár ez is lehet final

    protected static final Logger logger = Logger.getLogger("Player");

    // konstruktor
    protected Player(Room room, Game game) {
        this.where = room;
        this.items = new ArrayList<>();
        this.faintCountdown = 0;
        this.game = game;
    }
    /**
     *
     * @param rounds: number of rounds until they are protected
     */
    public void setProtectionFor(int rounds) {}

    /**
     *
     * @param index: chosen item that they want to pick
     * @return: item that they picked
     */
    protected Item getItem(int index) {     // inventory 1-5ig
        if (index < 1 || index > 5) { throw new IllegalArgumentException();}
        return items.get(index - 1);
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
        setFaintCountdown(3);
        dropItems();
    }

    /** currently moving player
     *
     */
    public void step() {    // Ez absztrakt a modell szerint, de akkor amúgy mégsem?
        if (faintCountdown > 0) {
            setFaintCountdown(faintCountdown - 1);
            return;
        }

        else if (faintCountdown == 0) {
            // IDK ITT MINEK KÉNE TÖRTÉNNIE

        }



    }

    /**
     *
     * @param item: picked item that they will dispose of
     */
    public void removeFromInventory(Item item) {
        item.setWhichRoom(where);
        items.remove(item);
    }

    /**
     *
     * @param item: picked item that they will pick up
     */
    public void addToInventory(Item item) {
        item.setWhichRoom(null);    // az elemhez tartozó szobát null-ra állítjuk
        items.add(item);            // felvesszük a tárgyat az inventoryba
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
        where.getOccupants().remove(this);  // a kilépendő szobából eltávolítja a játékost
        setWhere(room);                         // a játékos átlép az új szobába
        where.getOccupants().add(this);         // az új szobához hozzáadjuk a játékost
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
    private boolean pickItem(int index) {
        if (where.getItem(index) == null) {
            return false;
        }
        else {
            where.getItem(index).pickup(this);
            return true;
        }
    }

    /**
     *
     * @param index: identifier of item they want to put down
     */
    private boolean disposeItem(int index) {
        Item item = getItem(index);
        if(item == null) {
            return false;
        }
        else {
            where.addItem(item);
            return true;
        }
    }

    private void moveTowards (Direction direction) {}
    public void dropItems() {
        for (Item item : items) {
            item.setOwner(null);            // ha eldobja nem lesz senkié sem
            items.remove(item);             // a játékostól elvesszük az elemet
            item.setWhichRoom(where);       // az tárgyhoz beállítjuk a szobát, amelyben a játékos eldobta a tárgyat
            where.getItems().add(item);     // a szobához hozzáadjuk a tárgyat
        }

    }
    public void scheduleDrop() {}           // EZ A TERVBEN NINCS BENNE
    private void consoleAct() {}
    private void consoleMove() {}
    private void consoleMoveTowards(Direction direction) {}
    protected abstract boolean useItem(int index);      // Ez nincs kifejtve a tervbem


    // getters and setters
    /**
     *
     * @return room: identifier of currently occupied room (by this player)
     */
    public Room getWhere() { return new Room(); }       // ezt refaktoráltam where -> getWhere
    public List<Item> getItems() { return items; }     // ehhez setter nem kell
    public int getFaintCountdown() { return faintCountdown; }
    public Game getGame() { return game; }        // ez protected volt (miert?)

    /**
     *
     * @param location: room where they are
     */
    public void setWhere(Room location) { where = location; }   // Ez hiányzik a modellből
    public void setFaintCountdown(int n) { faintCountdown = n; }





    @Override
    public abstract String toString();      // Ez gondolom csak a skeletonhoz kellett
}
