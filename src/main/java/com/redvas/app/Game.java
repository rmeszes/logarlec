package com.redvas.app;

import com.redvas.app.map.Labyrinth;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game {
    protected static final Logger logger = Logger.getLogger("Game");

    static {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINEST);
        logger.addHandler(handler);
        logger.setLevel(Level.FINEST);
    }

    public Game() {
        logger.fine("Game init");
        createLabyrinth();
    }

    private List<Steppable> getSteppables() { return new ArrayList<>(); }

    private void playRound() {
        logger.fine("Új kör indulás");
        for (Steppable s : getSteppables())
            s.step();
    }

    private void createLabyrinth() {
        /* this.labyrinth = */new Labyrinth();
    }

    public static void undergraduateVictory() {
        logger.fine("Undergraduate team won the game!");
    }

    public static void professorVictory() {
        logger.fine("Professor team won the game!");
    }
}
