package com.redvas.app.ui;

import com.redvas.app.map.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.*;

public class ActionPanel extends JPanel {

    private JLabel playerLabel;
    private JLabel[] playerItemsLabels;
    private JLabel[] roomItemsLabels;
    private JButton[] moveButtons;


    public ActionPanel() {
        setLayout(new GridLayout(5, 6));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        playerLabel = new JLabel();
        playerItemsLabels = new JLabel[5];
        roomItemsLabels = new JLabel[5];
        moveButtons = new JButton[4];


        add(playerLabel); // 1. sor 1. oszlop

        for (int i = 0; i < playerItemsLabels.length; i++) {
            playerItemsLabels[i] = new JLabel();
            add(playerItemsLabels[i]); // 1. sor 2-6. oszlop
        }

        // 2. sor
        for (int i = 0; i < 6; i++) {
            add(new JLabel(""));
        }

        // 3. sor
        add(new JLabel("Room Items:"));
        for (int i = 0; i < roomItemsLabels.length; i++) {
            roomItemsLabels[i] = new JLabel();
            add(roomItemsLabels[i]);
        }

        // 4. sor
        for (int i = 0; i < 6; i++) {
            add(new JLabel(""));
        }

        // 5. sor
        add(new JLabel("Move:"));
        String[] moveDirections = {"Up", "Down", "Left", "Right"};
        for (int i = 0; i < moveButtons.length; i++) {
            moveButtons[i] = new JButton(moveDirections[i]);
            moveButtons[i].setPreferredSize(new Dimension(100, 30));
            add(moveButtons[i]);
        }

        updatePanel(0, new ImageIcon[5], new ImageIcon[5], new Direction[4]);
    }

    public void updatePanel(int playerNum, ImageIcon[] playerItems, ImageIcon[] roomItems, Direction[] dirs) {
        playerLabel.setText("Player " + playerNum + " items:");

        for (int i = 0; i < playerItemsLabels.length; i++) {
            if (i < playerItems.length)
                playerItemsLabels[i].setIcon(playerItems[i]);
        }

        for (int i = 0; i < roomItemsLabels.length; i++) {
            if (i < roomItems.length)
                roomItemsLabels[i].setIcon(roomItems[i]);
        }
        for (int i = 0; i < 4; i++) {
            // gomb disable logika ide
        }
    }
}

