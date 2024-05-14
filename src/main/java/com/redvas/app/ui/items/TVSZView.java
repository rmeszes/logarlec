package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.TVSZ;
import com.redvas.app.ui.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class TVSZView extends JPanel implements ItemChangeListener, View {
    private static final Logger logger = App.getConsoleLogger(TVSZView.class.getName());
    private final TVSZ tvsz;
    private int x;
    private int y;
    BufferedImage tvszImage;

    public TVSZView(TVSZ tvsz, int x, int y) {
        this.tvsz = tvsz;
        this.x = x;
        this.y = y;
        try {
            tvszImage = ImageIO.read(new File("src/main/resources/items/tvsz.png"));
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(tvszImage, 0, 0, 100, 100, null);
    }
    @Override
    public void draw() {
        repaint();
    }

    @Override
    public void positionChanged(boolean isInRoom) {

    }
}
