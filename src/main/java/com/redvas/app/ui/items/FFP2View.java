package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.FFP2;

import javax.imageio.ImageIO;
import javax.swing.text.View;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class FFP2View implements ItemChangeListener{
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
            ffp2Image = ImageIO.read(new File("src/main/resources/floor.png"));
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }

    public void draw(Graphics2D g) {

    }

    @Override
    public void positionChanged(int newX, int newY) {

    }
}
