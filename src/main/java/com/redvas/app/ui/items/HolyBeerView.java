package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.HolyBeer;
import com.redvas.app.ui.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class HolyBeerView extends JPanel implements ItemChangeListener, View {
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
@Override
    public void draw() {
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(beerImage, 0, 0, 100, 100, null);
    }

    @Override
    public void positionChanged(boolean isInRoom) {

    }
}
