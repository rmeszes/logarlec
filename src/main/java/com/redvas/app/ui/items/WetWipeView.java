package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.WetWipe;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class WetWipeView implements ItemChangeListener{
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
            wipeImage = ImageIO.read(new File("src/main/resources/floor.png"));
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
