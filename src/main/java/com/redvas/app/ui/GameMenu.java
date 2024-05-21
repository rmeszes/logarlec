package com.redvas.app.ui;

import com.redvas.app.Game;
import com.redvas.app.players.Undergraduate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameMenu extends JFrame {
    final GameWindow[] gameWindowContainer = new GameWindow[1];
    int PlayerCount = 2;    // default

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
                        // ehhez külön ablak ahol megkérdezzük a fe
                        setPlayerCount();

                        int width = 5, height = 5;
                        gameWindowContainer[0] = new GameWindow(width, height, PlayerCount);   // így el tudjuk tárolni a GameWindow-ot
                        Game game = gameWindowContainer[0].gamePanel.generator.getGame();    // ezeket mind publicra tettem

                        Undergraduate testPlayer = game.labyrinth.getTestPlayer();
                        gameWindowContainer[0].gamePanel.playerToMove = testPlayer;

                        // MÛKÖDIK a konzollal ezzel a megoldással


                    });
                    //dispose();
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


        // Kilépések kezelésére
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

    public void setPlayerCount() {
        Integer[] playerOptions = {1, 2, 3, 4, 5, 6};
        JComboBox<Integer> playerCountComboBox = new JComboBox<>(playerOptions);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Number of players:"));
        panel.add(playerCountComboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Set Game Parameters",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            PlayerCount = (int) playerCountComboBox.getSelectedItem();
            dispose();
            System.out.println("playerCount: " +  PlayerCount);
        } else {

            return;
        }
    }


}
