package com.redvas.app;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UnderGraduate extends Player{

    protected static final Logger logger = Logger.getLogger("UnderGraduate");

    static {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINEST);
        logger.addHandler(handler);
        logger.setLevel(Level.FINEST);
    }
    @Override
    public void faint() {
        logger.fine("Elájult és elejtett minden tárgyat");
    }

    public void getItem(int itemIndex) {
        logger.fine("Kiválasztott item index: " + itemIndex);
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

    public void mergePickedItem() {
        //TODO: no idea rn
    }

    void dropOut() {
        logger.fine("kiesett az egyetemről.");
    }
}
