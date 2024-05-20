package com.redvas.app.ui;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    public GamePanel gamePanel;
    private InventoryPanel inventoryPanel;    // majd kell egy ilyesmi osztály

    public GameWindow(int gameWidth, int gameHeight, int players) {
        setTitle("Logarlec");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        gamePanel = new GamePanel(gameWidth, gameHeight, players);
        inventoryPanel = new InventoryPanel();

        add(gamePanel, BorderLayout.CENTER);
        add(inventoryPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null); // Center the window
        setVisible(true);

        repaint();
    }

}
