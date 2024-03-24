package com.redvas.app.players;

import com.redvas.app.items.Item;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Undergraduate extends Player {
    /**
     *
     * @return int: how long are they protected from profs
     */
    private int getProtectedRounds() { return 0; }

    /**
     *
     * @param rounds: how long are they protected from profs
     */
    private void setProtectedRounds(int rounds) {}

    /**
     *
     * @param rounds: number of rounds until they are protected
     */
    @Override
    public void setProtectionFor(int rounds) {
        setProtectedRounds(rounds);
    }

    @Override
    public void pickLogarlec() {}

    protected static final Logger logger = Logger.getLogger("UnderGraduate");

    static {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINEST);
        logger.addHandler(handler);
        logger.setLevel(Level.FINEST);
    }

    /** has fainted and dropped all their items
     *
     */
    @Override
    public void faint() {
        logger.fine("Has fainted and dropped all items.");
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
        else
            logger.fine("Undergraduate object is dropped out");
    }

    /**
     *
     * @param index: picked items index
     */
    @Override
    protected void useItem(int index) {
        logger.fine(() -> this + " chose to use a(n) " + getItem(index));
        getItem(index).use();
    }


    public void mergeItems() {
        //TODO: no idea rn
    }

    /**
     * ?????????
     */
    void dropOut() {
        logger.fine(() -> this + " dropped out");
    }

    @Override
    public String toString() {
        return "Undergraduate";
    }
}
