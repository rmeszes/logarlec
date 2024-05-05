package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.AirFreshener;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class AirFreshenerView {
    private static final Logger logger = App.getConsoleLogger(AirFreshenerView.class.getName());
    private final AirFreshener airFreshener;
    private int x;
    private int y;

    BufferedImage airfreshenerImage;

    public AirFreshenerView(AirFreshener airFreshener, int x, int y) {
        this.airFreshener = airFreshener;
        this.x = x;
        this.y = y;
        try {
            airfreshenerImage = ImageIO.read(new File("src/main/resources/floor.png"));
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }
}
