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

    ArrayList<Steppable> SteppablesForRound = new ArrayList<>();

    private Game() {
        System.out.print("Player1 Name: ");
        String player1Name = App.reader.nextLine();
        System.out.printf("Player1 name set to %s (not really , todo)%n", player1Name);

        System.out.print("Player2 Name: ");
        String player2Name = App.reader.nextLine();
        System.out.printf("Player2 name set to %s (not really yet, todo)%n", player2Name);

        System.out.println("Player names set.");

        //folyt köv.
    }

    private Game(String arg) {
        System.out.printf("Loading game %s%n", arg);
    }

    private Game(int arg) {
        System.out.printf("Loading preset: %d%n", arg);
    }

    public static Game startNewGame() {
        return new Game();
    }

    public static Game loadGame(String arg) {
        return new Game(arg);
    }

    public static Game loadPreset(int arg) {
        return new Game(arg);
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
