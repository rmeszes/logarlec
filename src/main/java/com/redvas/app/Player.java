package com.redvas.app;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

//absztrakt class, majd az implementációk lesznek tesztelve
public abstract class Player {
    private static final Logger logger = Logger.getLogger("Player");

    static {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINEST);
        logger.addHandler(handler);
        logger.setLevel(Level.FINEST);
    }
    public void faint() {
        logger.fine("Fainted");
    }

    public void step() {
        logger.fine("Ready for next round");
    }

    public void removeFromInventory(Item item) {
        logger.fine("Item eldobva"); //TODO: itt kéne az item implementáció
    }

    public void addToInventory(Item item) {
        logger.fine("Item felvéve"); //TODO: itt kéne az item implementáció
    }

    public Room where() {
        System.out.print("Melyik szobában van a player?");
        String input = App.reader.nextLine();
        logger.fine("A játékos a(z) " + input + "szobában van");
        return new Room(); //TODO: Room implementation
    }

    public void moveTo(Room room) {
        logger.fine("moved room"); //TODO: megint kéne implementáció
    }

    public void useFFP2() {
        logger.fine("used FFP2 mask.");
    }
}
