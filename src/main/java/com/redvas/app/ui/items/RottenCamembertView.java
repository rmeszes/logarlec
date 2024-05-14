package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.RottenCamembert;
import com.redvas.app.ui.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class RottenCamembertView extends JPanel implements ItemChangeListener, View {
    private static final Logger logger = App.getConsoleLogger(RottenCamembertView.class.getName());
    private final RottenCamembert camembert;
    private int x;
    private int y;
    BufferedImage camembertImage;

    public RottenCamembertView(RottenCamembert rottenCamembert, int x, int y) {
        this.camembert = rottenCamembert;
        this.x = x;
        this.y = y;
        try {
            camembertImage = ImageIO.read(new File("src/main/resources/items/rotten_camembert.png"));
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }
@Override
    public void draw() {
repaint();
    }

    @Override
    public void positionChanged(boolean isInRoom) {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(camembertImage, 0, 0, 100, 100, null);
    }
}
