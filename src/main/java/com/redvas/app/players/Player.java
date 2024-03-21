package com.redvas.app.players;

import com.redvas.app.App;
import com.redvas.app.Game;
import com.redvas.app.map.Room;
import com.redvas.app.Steppable;
import com.redvas.app.items.Item;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

//absztrakt class, majd az implementációk lesznek tesztelve
public abstract class Player implements Steppable {
    protected Game getGame() { return new Game(); }
    protected static final Logger logger = Logger.getLogger("Player");

    public void setProtectionFor(int rounds) {
            // nem csinal semmit
    }

    public abstract void pickLogarlec();

    static {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINEST);
        logger.addHandler(handler);
        logger.setLevel(Level.FINEST);
    }
    public void faint() {}

    public void step() {
        logger.fine("Kész a következő körre");
    }

    public void removeFromInventory(Item item) {
        logger.fine("Item eldobva"); //TODO: itt kéne az item implementáció
    }

    public void addToInventory(Item item) {
        logger.fine("Item felvéve"); //TODO: itt kéne az item implementáció
    }

    public abstract void undergraduateVictory();

    public abstract void professorVictory();

    public abstract void paralyze();
    public abstract void dropout();

    public Room where() {
        System.out.print("Melyik szobában van a player?");
        String input = App.reader.nextLine();
        logger.fine("A játékos a(z) " + input + "szobában van");
        return new Room(); //TODO: Room implementation
    }

    public void moveTo(Room room) {
        logger.fine("szobát lépett"); //TODO: megint kéne implementáció
    }

    public void useFFP2() {
        logger.fine("használt egy FFP2-es maszkot");
    }

    public void winGame() {
        logger.fine("hallgatók megnyerték a játékot!");
        //TODO: not sure még hogy ezt hogy csináljuk, boilerplatet írok rn
    }
}
