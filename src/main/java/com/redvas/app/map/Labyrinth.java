package com.redvas.app.map;

import com.redvas.app.Steppable;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Labyrinth implements Steppable {
    protected static final Logger logger = Logger.getLogger("Labyrinth");

    static {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINEST);
        logger.addHandler(handler);
        logger.setLevel(Level.FINEST);
    }

    private void update() {
        logger.fine("Labyrinth is changing.");
    }

    public Labyrinth() {
        logger.fine("Labyrinth created");
        generateRooms();
    }

    /** calls the update on every object
     * and asks players to move (later)
     *
     */
    @Override
    public void step() {
        logger.fine("Labyrinth step");
        update();
    }

    /** initialization
     *
     */
    private void generateRooms() {
        logger.fine("Room generation start");
        //majd itt csinálunk szobákat, most egyet tesztnek
        // ezt még lehet máshogy kell megoldani, mert össze is kéne őket kötni, de sztem itt még nem, az már state
        new Room();

        new Door();
    }
}
