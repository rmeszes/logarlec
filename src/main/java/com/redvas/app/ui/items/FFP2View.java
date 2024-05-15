package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.FFP2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class FFP2View extends JPanel implements ItemChangeListener {
    private static final Logger logger = App.getConsoleLogger(FFP2View.class.getName());
    private final FFP2 ffp2;
    private int x;
    private int y;
    BufferedImage ffp2Image;

    public FFP2View(FFP2 ffp2, int x, int y) {
        this.ffp2 = ffp2;
        this.x = x;
        this.y = y;
        try {
            ffp2Image = ImageIO.read(new File("src/main/resources/items/ffp2.png"));
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(ffp2Image, 0, 0, 100, 100, null);
    }

    @Override
    public void positionChanged(boolean isInRoom) {

    }
}
