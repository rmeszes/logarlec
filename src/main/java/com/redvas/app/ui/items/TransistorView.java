package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.Transistor;
import com.redvas.app.ui.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class TransistorView extends JPanel implements ItemChangeListener, View {
    private static final Logger logger = App.getConsoleLogger(TransistorView.class.getName());
    private final Transistor transistor;
    private int x;
    private int y;
    BufferedImage transistorImage;

    public TransistorView(Transistor t, int x, int y) {
        this.transistor = t;
        this.x = x;
        this.y = y;
        try {
            transistorImage = ImageIO.read(new File("src/main/resources/items/transistor.png"));
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(transistorImage, 0, 0, 100, 100, null);
    }
    @Override
    public void draw() {
        repaint();
    }

    @Override
    public void positionChanged(boolean isInRoom) {

    }
}
