package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.RottenCamembert;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class RottenCamembertView implements ItemChangeListener{
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

    public void draw(Graphics2D g) {

    }

    @Override
    public void positionChanged(boolean isInRoom) {

    }
}
