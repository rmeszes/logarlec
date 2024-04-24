package com.redvas.app;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Game {
    protected static final Logger logger = App.getConsoleLogger(Game.class.getName());

    ArrayList<Steppable> steppablesForRound = new ArrayList<>();

    public Game() {
        /*System.out.print("Player1 Name: ");
        Undergraduate player1 = new Undergraduate(App.reader.nextLine());
        SteppablesForRound.add(player1);
        System.out.printf("Player1 name set to %s%n", player1.getName());

        System.out.print("Player2 Name: ");
        Undergraduate player2 = new Undergraduate(App.reader.nextLine());
        SteppablesForRound.add(player2);
        System.out.printf("Player2 name set to %s%n", player2.getName());

        System.out.println("Player names set.");

        //folyt köv. */
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

    private int undergraduates = 0;
    private boolean end = false;

    public void undergraduateDroppedout() {
        if (--undergraduates == 0)
            professorVictory();
    }
}
