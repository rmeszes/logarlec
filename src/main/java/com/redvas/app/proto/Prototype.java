package com.redvas.app.proto;

import com.redvas.app.App;
import com.redvas.app.Game;
import com.redvas.app.ui.GameMenu;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Prototype {

    private static final Logger logger = App.getConsoleLogger(Prototype.class.getName());
    private Game game;

    private static final Scanner stdin = App.reader;

    public Prototype(boolean _isGraphicsMenu) throws ParserConfigurationException, TransformerException, IOException, ClassNotFoundException, InvocationTargetException, SAXException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        logger.fine("\nApp started successfully.");

        if(_isGraphicsMenu) {       // Ha grafikus menüvel indítjuk
            SwingUtilities.invokeLater(() -> {
                GameMenu gameMenu = new GameMenu(this);
            });
        }
        else {      // Ha a commandLine-ból szeretnénk vezérelni
            menu();
        }

    }

    private void menu() throws ParserConfigurationException, TransformerException, IOException, ClassNotFoundException, InvocationTargetException, SAXException, NoSuchMethodException, InstantiationException, IllegalAccessException {
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
                case "start" -> commandStart();     // meghívja a game ctor-ját menü nélkül
                case "load" -> commandLoad(inputSplit[1]);
                case "quit" -> commandQuit();
                default -> {
                    badInput = true;
                    logger.warning("\nUnknown command");
                }
            }
        } while(badInput);
    }

    public void commandStart() throws ParserConfigurationException, TransformerException, IOException, ClassNotFoundException, InvocationTargetException, SAXException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        game = Game.startNewGame(false);
    }

    public void menuStart() throws ParserConfigurationException, TransformerException, IOException, ClassNotFoundException, InvocationTargetException, SAXException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        game =  Game.startNewGame(true);
    }

    private void commandLoad(String arg) throws ParserConfigurationException, TransformerException, IOException, ClassNotFoundException, InvocationTargetException, SAXException, NoSuchMethodException, InstantiationException, IllegalAccessException{
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
