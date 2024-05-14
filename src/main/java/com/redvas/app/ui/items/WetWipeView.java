package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.WetWipe;
import com.redvas.app.ui.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class WetWipeView extends JPanel implements ItemChangeListener, View {
    private static final Logger logger = App.getConsoleLogger(WetWipeView.class.getName());
    private final WetWipe wipe;
    private int x;
    private int y;
    BufferedImage wipeImage;

    public WetWipeView(WetWipe wetWipe, int x, int y) {
        this.wipe = wetWipe;
        this.x = x;
        this.y = y;
        try {
            wipeImage = ImageIO.read(new File("src/main/resources/items/wetwipe.png"));
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(wipeImage, 0, 0, 100, 100, null);
    }
    @Override
    public void draw() {
        repaint();
    }

    @Override
    public void positionChanged(boolean isInRoom) {

    }
}
