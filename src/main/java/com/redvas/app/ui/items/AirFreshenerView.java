package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.AirFreshener;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class AirFreshenerView extends JPanel implements ItemChangeListener {
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
            freshenerImage = ImageIO.read(new File("src/main/resources/item/airfreshener.png"));
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }


    @Override
    public void positionChanged(boolean isInRoom) {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(freshenerImage, 0, 0, 100, 100, null);
    }
}
