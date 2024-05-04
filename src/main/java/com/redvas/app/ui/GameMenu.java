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
    public Prototype prototype;

    public GameMenu(Prototype _prototype) {
        prototype = _prototype;

        // Set up the main frame
        setTitle("Game Menu");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        JButton startGameButton = new JButton("Start new game");
        JButton loadPresetGamestateButton = new JButton("Load preset gamestate");
        JButton loadSavedGameButton = new JButton("Load saved game");
        JButton exitButton = new JButton("Exit");

        // Add action listeners
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    startNewGame();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        loadPresetGamestateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //addPlayers();
            }
        });

        loadSavedGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //showPlayerList();
            }
        });


        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //savePlayers();
                System.exit(0);
            }
        });


        // Kilépések kezelésére
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //savePlayers();
                System.exit(0);
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
    }
}
