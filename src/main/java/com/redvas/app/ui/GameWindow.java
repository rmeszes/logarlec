package com.redvas.app.ui;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    public GamePanel gamePanel;
    private static ActionPanel actionPanel;    // majd kell egy ilyesmi osztály

    public GameWindow(int gameWidth, int gameHeight, int players) {
        setTitle("Logarlec");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        gamePanel = new GamePanel(gameWidth, gameHeight, players);
        //actionPanel = new ActionPanel();

        add(gamePanel, BorderLayout.CENTER);
        //add(actionPanel, BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null); // Center the window
        setVisible(true);

        repaint();
    }

}
