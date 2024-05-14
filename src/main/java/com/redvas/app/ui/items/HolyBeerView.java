package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.HolyBeer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class HolyBeerView implements ItemChangeListener{
    private static final Logger logger = App.getConsoleLogger(HolyBeerView.class.getName());
    private final HolyBeer beer;
    private int x;
    private int y;
    BufferedImage beerImage;

    public HolyBeerView(HolyBeer holyBeer, int x, int y) {
        this.beer = holyBeer;
        this.x = x;
        this.y = y;
        try {
            beerImage = ImageIO.read(new File("src/main/resources/items/holybeer.png"));
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
