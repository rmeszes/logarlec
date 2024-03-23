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
        logger.fine("Labirintus objektumra kerult a sor, atalakul");
    }

    public Labyrinth() {
        logger.fine("Labyrinth created");
        generateRooms();
    }
  
    @Override
    public void step() {
        logger.fine("Labyrinth step");
        update();
    }

    private void generateRooms() {
        logger.fine("Room generation start");
        //majd itt csinálunk szobákat, most egyet tesztnek
        // ezt még lehet máshogy kell megoldani, mert össze is kéne őket kötni, de sztem itt még nem, az már state
        new Room();

        //ide még esetleg jöhetne egy door is?
        new Door();
    }
}
