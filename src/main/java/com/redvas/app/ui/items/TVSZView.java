package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.TVSZ;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class TVSZView implements ItemChangeListener{
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

    public void draw(Graphics2D g) {

    }

    @Override
    public void positionChanged(boolean isInRoom) {

    }
}
