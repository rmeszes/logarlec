package com.redvas.app;

import com.redvas.app.map.Labyrinth;
import com.redvas.app.players.Player;
import com.redvas.app.players.Undergraduate;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game {
    protected static final Logger logger = Logger.getLogger("Game");

    ArrayList<Steppable> SteppablesForRound = new ArrayList<>();

    String[] playerNames = new String[2];

    private Game() {
        SteppablesForRound.add(new Undergraduate());
        SteppablesForRound.add(new Undergraduate());

        System.out.print("Player1 Name: ");
        playerNames[0] = App.reader.nextLine();
        System.out.printf("Player1 name set to %s%n", playerNames[0]);

        System.out.print("Player2 Name: ");
        playerNames[1] = App.reader.nextLine();
        System.out.printf("Player2 name set to %s%n", playerNames[1]);

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
