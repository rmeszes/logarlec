package com.redvas.app;

import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game {
    protected static final Logger logger = Logger.getLogger("Professor");

    static {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINEST);
        logger.addHandler(handler);
        logger.setLevel(Level.FINEST);
    }
    private List<Steppable> getSteppables() {}

    private void playRound() {
        for (Steppable s : getSteppables())
            s.step();
    }

    public void undergraduateVictory() {
        logger.fine("Undergraduate team won the game!");
    }

    public void professorVictory() {
        logger.fine("Professor team won the game!");
    }
}
