package com.redvas.app;

import com.redvas.app.map.Labyrinth;
import com.redvas.app.map.Room;
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

    public Game() {         // ez át lett írva publicra
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
