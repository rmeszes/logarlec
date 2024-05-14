package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.CombinedTransistor;
import com.redvas.app.ui.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class CombinedTransistorView extends JPanel implements ItemChangeListener, View {
    private static final Logger logger = App.getConsoleLogger(CombinedTransistorView.class.getName());
    private final CombinedTransistor ctransistor;
    private int x;
    private int y;
    BufferedImage ctransistorImage;

    public CombinedTransistorView(CombinedTransistor ctransistor, int x, int y) {
        this.ctransistor = ctransistor;
        this.x = x;
        this.y = y;
        try {
            ctransistorImage = ImageIO.read(new File("src/main/resources/items/transistor.png"));
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }


    }
    @Override
    public void positionChanged(boolean isInRoom) {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(ctransistorImage, 0, 0, 100, 100, null);
    }
    @Override
    public void draw() {
        repaint();
    }
}
