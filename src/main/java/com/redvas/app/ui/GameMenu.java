package com.redvas.app.ui;

import com.redvas.app.App;
import com.redvas.app.Game;
import com.redvas.app.players.Undergraduate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Logger;

public class GameMenu extends JFrame implements GameOverListener {
    private static final Logger logger = App.getConsoleLogger(GameMenu.class.getName());
    @Override
    public void onGameOver(boolean undergradVictory){
        gameWindowContainer[0].dispose();
        this.setVisible(true);
    }
    private void createGameWindow(int width, int height){
        gameWindowContainer[0] = new GameWindow(width, height, playerCount);
        gameWindowContainer[0].gamePanel.generator.getGame().setGOListener(this);
    }
    public static final GameWindow[] gameWindowContainer = new GameWindow[1];
    int playerCount = 2;    // default

    public GameMenu() {
       // Set up the main frame
        setTitle("Game Menu");
        setSize(300, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Create components
        JButton startGameButton = new JButton("Start new game");
        JButton loadSavedGameButton = new JButton("Load saved game");
        JButton exitButton = new JButton("Exit");

        // Add action listeners
        startGameButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            // ehhez külön ablak ahol megkérdezzük a felhasználót
            setPlayerCount();

            int width = 5;
            int height = 5;
            createGameWindow(width, height);
            Game game = gameWindowContainer[0].gamePanel.generator.getGame();    // ezeket mind publicra tettem

            gameWindowContainer[0].gamePanel.playerToMove = game.labyrinth.getTestPlayer();

            // MŰKÖDIK a konzollal ezzel a megoldással


        }));

        loadSavedGameButton.addActionListener(e -> {
            //TODO betöltés
            Game game = gameWindowContainer[0].gamePanel.generator.getGame();
        });


        exitButton.addActionListener(e -> System.exit(0));


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
            assert(playerCountComboBox.getSelectedItem() != null);
            playerCount = (int) playerCountComboBox.getSelectedItem();
            dispose();
            logger.fine(()->"playerCount: " + playerCount);
        }
    }



}
