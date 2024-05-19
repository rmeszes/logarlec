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
                    startNewGame();
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

    private void startNewGame() {
        /*SwingUtilities.invokeLater(() -> {
            // Create a new frame for the game panel
            JFrame gameFrame = new JFrame("Game");
            GamePanel gamePanel = new GamePanel(5, 5, 4);  // Create game panel with specific dimensions and players
            gameFrame.add(gamePanel);  // Add game panel to the frame
            gameFrame.pack();  // Pack the frame to adjust to the preferred size of the game panel
            gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Ensure closing this frame doesn't exit the application
            gameFrame.setResizable(false);  // Disable resizing
            gameFrame.setVisible(true);  // Make the game frame visible
        });
         */
        GamePanel gamePanel = new GamePanel(5, 5, 4);
        dispose();
    }

}
