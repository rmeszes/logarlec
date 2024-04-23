package com.redvas.app.proto;

import com.redvas.app.App;
import com.redvas.app.Game;

import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Prototype {

    private static final Logger logger = App.getConsoleLogger(Prototype.class.getName());
    private Game game;

    private static final Scanner stdin = App.reader;

    public Prototype() {
        logger.fine("\nApp started successfully.");

        menu();
    }

    private void menu() {
        boolean badInput;

        logger.fine("""
                
                Type 'start' to start a new game,
                'load #' to load a preset gamestate,
                'load save' to load a saved game  or
                'quit' to exit the game
                """);

        do {
            String input = stdin.nextLine();
            String[] inputSplit = input.split(" "); //need this so I can make the switch statement

            badInput = false;
            switch(inputSplit[0]) {
                case "start" -> commandStart();
                case "load" -> commandLoad(inputSplit[1]);
                case "quit" -> commandQuit();
                default -> {
                    badInput = true;
                    logger.warning("\nUnknown command");
                }
            }
        } while(badInput);
    }

    private void commandStart() {
        game = Game.startNewGame();
    }

    private void commandLoad(String arg) {
        try {
            int i = Integer.parseInt(arg);
            game = Game.loadPreset(i);
        } catch (NumberFormatException ignored) {
            game = Game.loadGame(arg);
        }
    }

    private void commandQuit() {
        System.exit(0);
    }
}
