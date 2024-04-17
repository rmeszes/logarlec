package com.redvas.app.proto;

import com.redvas.app.App;
import com.redvas.app.Game;

import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Prototype {
    Game game;

    Scanner stdin = App.reader;

    public Prototype() {
        System.out.println("App started successfully.");

        menu();
    }

    private void menu() {
        boolean badInput;

        System.out.println("""
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
                    System.out.println("Unknown command");
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
