package com.redvas.app.ui;

import com.redvas.app.map.Labyrinth;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private final transient Labyrinth labyrinth;

    public GamePanel(Labyrinth labyrinth) {
        this.labyrinth = labyrinth;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Cast the Graphics object to Graphics2D
        Graphics2D g2d = (Graphics2D) g;

        // Call the draw method of the labyrinth
        labyrinth.draw(g2d);
    }


}