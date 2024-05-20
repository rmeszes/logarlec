package com.redvas.app.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InventoryPanel extends JPanel {

    private JLabel[] slots = new JLabel[5];
    private ImageIcon[] images = new ImageIcon[5];
    private int selectedSlot = -1;

    public InventoryPanel() {
        setLayout(new GridLayout(1, 5, 5, 5));
        setPreferredSize(new Dimension(300, 50)); // Adjust size as needed

        for (int i = 0; i < slots.length; i++) {
            slots[i] = new JLabel();
            slots[i].setPreferredSize(new Dimension(100, 100));
            slots[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            slots[i].setHorizontalAlignment(JLabel.CENTER);
            slots[i].setVerticalAlignment(JLabel.CENTER);
            slots[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    handleSlotClick(e);
                }
            });
            add(slots[i]);
        }

        loadImages();
    }

    private void loadImages() {
        // Load your images here
        images[0] = new ImageIcon("path/to/image1.png");
        images[1] = new ImageIcon("path/to/image2.png");
        images[2] = new ImageIcon("path/to/image3.png");
        images[3] = new ImageIcon("path/to/image4.png");
        images[4] = new ImageIcon("path/to/image5.png");
    }

    private void handleSlotClick(MouseEvent e) {
        JLabel clickedSlot = (JLabel) e.getSource();
        int index = -1;

        for (int i = 0; i < slots.length; i++) {
            if (slots[i] == clickedSlot) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            if (selectedSlot == -1) {
                selectedSlot = index;
                slots[selectedSlot].setBorder(BorderFactory.createLineBorder(Color.RED));
            } else {
                slots[selectedSlot].setIcon(images[index]);
                slots[selectedSlot].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                selectedSlot = -1;
            }
        }
    }
}
