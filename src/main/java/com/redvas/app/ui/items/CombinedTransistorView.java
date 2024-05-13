package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.CombinedTransistor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class CombinedTransistorView {
    private static final Logger logger = App.getConsoleLogger(CombinedTransistorView.class.getName());
    private final CombinedTransistor ctransistor;
    private int x;
    private int y;
    BufferedImage ctransistorImage;

    public CombinedTransistorView(CombinedTransistor ctransistor, int x, int y) {
        this.ctransistor = ctransistor;
        this.x = x;
        this.y = y;
        try {
            ctransistorImage = ImageIO.read(new File("src/main/resources/floor.png"));
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }


    }
}
