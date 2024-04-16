package com.redvas.app;

import com.redvas.app.map.Labyrinth;
import com.redvas.app.players.Player;

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
    }

    private List<Steppable> getSteppables() { return new ArrayList<>(); }

    public void play() {}

    public void playRound() {
        logger.fine("New round");
        for (Steppable s : getSteppables())
            s.step();
    }

    public static void undergraduateVictory() {
        logger.fine("Undergraduate team won the game!");
    }

    public static void professorVictory() {
        logger.fine("Professor team won the game!");
    }

    public void eliminatePlayer(Player player) {

    }
}
