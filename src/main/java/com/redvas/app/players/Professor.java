package com.redvas.app.players;

import com.redvas.app.map.Room;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Professor extends Player {
    @Override
    public void moveTo(Room to) {
        logger.fine(this + " notifies its origin and target rooms about his/her transfer");
        where().professorLeft();
        to.professorEntered();
        super.moveTo(to);
    }

    @Override
    protected void useItem(int index) {
        // Nothing happens
    }

    protected static final Logger logger = Logger.getLogger("Professor");

    static {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINEST);
        logger.addHandler(handler);
        logger.setLevel(Level.FINEST);
    }

    @Override
    public void pickLogarlec() {
        // Nothing happens
    }

    @Override
    public void paralyze() {
        logger.fine(this + " is paralyzed");
    }

    @Override
    public void dropout() {
        // Nothing happens
    }

    @Override
    public String toString() {
        return "Professor";
    }
}
