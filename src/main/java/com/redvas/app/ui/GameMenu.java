package com.redvas.app.ui;

import com.redvas.app.Game;
import com.redvas.app.proto.Prototype;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class GameMenu extends JFrame {
    private static GameMenu gameMenu = null;
    public Prototype prototype;

    public static void createGameMenu(Prototype prototype) {
        gameMenu = new GameMenu(prototype);
    }

    public static GameMenu getGameMenu() {
        if(gameMenu == null) { throw new GameMenuNotCreatedException();}
        return gameMenu;
    }

    private GameMenu(Prototype prototype) {
        this.prototype = prototype;

        // Set up the main frame
        setTitle("Game Menu");
        setSize(300, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Create components
        JButton startGameButton = new JButton("Start new game");
        JButton loadPresetGamestateButton = new JButton("Load preset gamestate");
        JButton loadSavedGameButton = new JButton("Load saved game");
        JButton exitButton = new JButton("Exit");

        // Add action listeners
        startGameButton.addActionListener(e -> {
            try {
                startNewGame();
            } catch (Exception ex) {
                System.exit(1);
            }
        });

        loadPresetGamestateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO loadtestsave
            }
        });

        loadSavedGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO loadsavedgame
            }
        });

        // Set up layout
        setLayout(new GridLayout(4, 1));
        add(startGameButton);
        add(loadPresetGamestateButton);
        add(loadSavedGameButton);
        add(exitButton);

        // Display the frame
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void startNewGame() throws ParserConfigurationException, IOException, ClassNotFoundException, TransformerException, InvocationTargetException, SAXException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        prototype.menuStart();
        //  prototype.commandStart();  // igy sem rajzolja ki a labirintust, NEM ERTEM MIERT, pedig ezt hivja a commandline-bol is a proto
    }
}
