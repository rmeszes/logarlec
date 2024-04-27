package com.redvas.app.players;

import com.redvas.app.App;
import com.redvas.app.Game;
import com.redvas.app.map.Rooms.Room;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.logging.Logger;

public class Undergraduate extends Player {
    // tagváltozók
    private String name;
    private int protection;
    private boolean dropScheduled;
    public Undergraduate(Integer id, String name, Room room, Game game) {
        super(id, room, game);
        this.name = name;
        this.protection = 0;
        this.dropScheduled = false;
        logger.fine(() -> this + " created");
    }

    public Undergraduate(Integer id, Room room, Game game) {
        super(id, room, game);
        this.protection = 0;
        this.dropScheduled = false;
        logger.fine(() -> this + " created");
    }

    @Override
    public Element saveXML(Document document) {
        Element undergraduate = super.saveXML(document);
        undergraduate.setAttribute("protection", String.valueOf(protection));
        return undergraduate;
    }

    /**
     *
     * @return int: how long are they protected from profs
     */
    private int getProtectedRounds() { return protection; }

    /**
     *
     * @param rounds: how long are they protected from profs
     */
    private void setProtectedRounds(int rounds) {
        if(rounds > protection) protection = rounds;
    }

    /**
     *
     * @param rounds: number of rounds until they are protected
     */
    @Override
    public void setProtectionFor(int rounds) {
        setProtectedRounds(rounds);
    }

    /** calls the win function
     *
     */
    @Override
    public void pickLogarlec() {
        getGame().undergraduateVictory();
    }

    protected static final Logger logger = App.getConsoleLogger(Undergraduate.class.getName());

    /** has fainted and dropped all their items
     *
     */
    @Override
    public void faint() {
        logger.fine("Has fainted and dropped all items.");
    }

    @Override
    public void step() {
        logger.fine(() -> name + " move: ");
        App.reader.nextLine();
    }

    /** only profs can be
     *
     */
    @Override
    public void paralyze() {
      // Nem csinal semmit
    }

    /** losing the game
     *
     */
    @Override
    public void dropout() {
        if (getProtectedRounds() > 0)
            logger.fine("Undergraduate was protected from being dropped out");
        else {
            logger.fine(()->name + " has dropped out");
            getGame().undergraduateDroppedout();
        }
    }

    /**
     *
     * @param index: picked items index
     */
    @Override
    public boolean useItem(int index) {
        logger.fine(() -> this + " chose to use a(n) " + getItem(index));
        getItem(index).use();
        return true;
    }

    /** two possible outcomes: 2 transistors merged
     * or nothing happens because player did not have 2 transistors
     *
     */
    public void mergeItems() {
        //TODO
    }

    // getter
    public String getName() { return name; }
    public int getProtection() { return protection; }
    public boolean getDropScheduled() { return dropScheduled; }



    /**
     *
     * @return string representation of this Player
     */
    @Override
    public String toString() {
        return getClass().getName() + " Name: " + getName();
    }
}
