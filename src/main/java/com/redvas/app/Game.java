package com.redvas.app;

import com.redvas.app.map.Labyrinth;
import com.redvas.app.players.Undergraduate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class Game {
    private static final Random random = new Random();
    protected static final Logger logger = App.getConsoleLogger(Game.class.getName());

    private ArrayList<Steppable> steppablesForRound = new ArrayList<>();

    Labyrinth labyrinth = new Labyrinth(random.nextInt(4,9), random.nextInt(4,9));

    public Game() {
        logger.fine("Player1 Name: ");
        String player1Name = App.reader.nextLine();
        logger.fine(() -> String.format("Player1 name set to %s%n", player1Name));

        logger.fine("Player2 Name: ");
        String player2Name = App.reader.nextLine();
        logger.fine(() -> String.format("Player2 name set to %s%n", player2Name));

        logger.fine("Player names set.");

        //folyt köv. 
    }

    private Game(String arg) {
        logger.fine(() -> String.format("Loading game %s%n", arg));
    }

    private Game(int arg) {
        logger.fine(() -> String.format("Loading preset: %d%n", arg));
    }

    /**
     * method for when the game is started from scratch
     */
    public static Game startNewGame() {
        return new Game();
    }

    /**
     * method for when the game has to load a previous save
     */
    public static Game loadGame(String arg) {
        return new Game(arg);
    }

    /**
     * method for when the game has to load a preset
     */
    public static Game loadPreset(int arg) {
        return new Game(arg);
    }

    private List<Steppable> getSteppables() { return new ArrayList<>(); }

    public void play() {
        while (!end)
            playRound();
    }

    public void playRound() {
        logger.fine("New round");

        for (Steppable s : getSteppables()) {
            s.step();

            if (end) return;
        }
    }

    public void undergraduateVictory() {
        logger.fine("Undergraduate team won the game!");
        end = true;
    }

    private void professorVictory() {
        logger.fine("Professor team won the game!");
        end = true;
    }

    private int undergraduates = 2;
    private boolean end = false;

    public void undergraduateDroppedout() {
        if (--undergraduates == 0)
            professorVictory();
    }
}
