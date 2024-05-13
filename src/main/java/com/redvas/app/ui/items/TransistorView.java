package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.Transistor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class TransistorView implements ItemChangeListener{
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
            transistorImage = ImageIO.read(new File("src/main/resources/floor.png"));
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }

    public void draw(Graphics2D g) {

    }

    @Override
    public void positionChanged(boolean isInRoom) {

    }
}
