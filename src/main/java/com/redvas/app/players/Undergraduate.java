package com.redvas.app.players;

import com.redvas.app.items.Item;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Undergraduate extends Player {
    private int getProtectedRounds() { return 0; }
    private void setProtectedRounds(int rounds) {}
    @Override
    public void setProtectionFor(int rounds) {
        setProtectedRounds(rounds);
    }
    protected static final Logger logger = Logger.getLogger("UnderGraduate");

    static {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINEST);
        logger.addHandler(handler);
        logger.setLevel(Level.FINEST);
    }

    @Override
    public void pickLogarlec() {
        getGame().undergraduateVictory();
    }

    @Override
    public void faint() {
        logger.fine("Elájult és elejtett minden tárgyat");
    }

    @Override
    public void paralyze() {
        // nothing happens
    }

    @Override
    public void dropout() {
        if (getProtectedRounds() > 0)
            logger.fine("Undergraduate was protected from being dropped out");
        else
            logger.fine("Undergraduate object is dropped out");
    }

    @Override
    protected void useItem(int index) {
        getItem(index).use();
    }

    public void usePickedItem(Item item) {
        item.use();
        logger.fine("használt egy tárgyat");
    }

    public void disposePickedItem(Item item) {
        item.use();
        logger.fine("eldobott egy tárgyat");
    }

    public void pickItem(Item item) {
        logger.fine("felvett egy tárgyat");
        //TODO: a logarléc part ha lesz implementáció
    }

    public void mergeItems() {
        //TODO: no idea rn
    }

    void dropOut() {
        logger.fine("kiesett az egyetemről.");
    }
}
