package com.redvas.app.ui;

import com.redvas.app.Game;
import com.redvas.app.map.rooms.Room;
import com.redvas.app.players.Undergraduate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicReference;

public class GameMenu extends JFrame {
    final GameWindow[] gameWindowContainer = new GameWindow[1];

    public GameMenu() {
       // Set up the main frame
        setTitle("Game Menu");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        JButton startGameButton = new JButton("Start new game");
        JButton loadSavedGameButton = new JButton("Load saved game");
        JButton exitButton = new JButton("Exit");

        // Add action listeners
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //SwingUtilities.invokeLater(() -> new GameWindow(5,5,4));
                    SwingUtilities.invokeLater(() -> {
                        gameWindowContainer[0] = new GameWindow(5, 5, 4);   // �gy el tudjuk t�rolni a GameWindow-ot
                        Game testgame = gameWindowContainer[0].gamePanel.generator.getGame();    // ezeket mind publicra tettem

                        Undergraduate testPlayer = testgame.labyrinth.getTestPlayer();
                        gameWindowContainer[0].gamePanel.playerToMove = testPlayer;
                    });
                    dispose();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        loadSavedGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ///TODO
            }
        });


        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.exit(0);
            }
        });


        // Kil�p�sek kezel�s�re
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // Set up layout
        setLayout(new GridLayout(3, 1));
        add(startGameButton);
        add(loadSavedGameButton);
        add(exitButton);

        // Display the frame
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
