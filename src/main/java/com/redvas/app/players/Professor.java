package com.redvas.app.players;

import com.redvas.app.map.Room;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Professor extends Player {
    @Override
    public void moveTo(Room to) {
        where().professorLeft();
        to.professorEntered();
        super.moveTo(to);
    }

    protected static final Logger logger = Logger.getLogger("Professor");

    static {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINEST);
        logger.addHandler(handler);
        logger.setLevel(Level.FINEST);
    }
    void paralyze(int rounds) { //TODO: doksiban nincs argumentum, azt át kell írni, vagy mindig ugyanannyit bénul?
        logger.fine("bénítva " + rounds + " körre!");
    }

    @Override
    public void step() {
        logger.fine("bot lépés");
    }

    @Override
    public void undergraduateVictory() {
        // nothing happens
    }

    @Override
    public void professorVictory() {
        getGame().professorVictory();
    }

    @Override
    public void paralyze() {
        logger.fine("Professor object is paralyzed");
    }

    @Override
    public void dropout() {
        // nothing happens
    }
}
