package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.Logarlec;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class LogarlecView implements ItemChangeListener{
    private static final Logger logger = App.getConsoleLogger(LogarlecView.class.getName());
    private final Logarlec logarlec;
    private int x;
    private int y;
    BufferedImage logarlecImage;

    public LogarlecView(Logarlec l, int x, int y) {
        this.logarlec = l;
        this.x = x;
        this.y = y;
        try {
            logarlecImage = ImageIO.read(new File("src/main/resources/items/logarlec.png"));
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
