package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.AirFreshener;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class AirFreshenerView implements ItemChangeListener{
    private static final Logger logger = App.getConsoleLogger(AirFreshenerView.class.getName());
    private final AirFreshener freshener;
    private int x;
    private int y;
    BufferedImage freshenerImage;

    public AirFreshenerView(AirFreshener a, int x, int y) {
        this.freshener = a;
        this.x = x;
        this.y = y;
        try {
            freshenerImage = ImageIO.read(new File("src/main/resources/floor.png"));
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
