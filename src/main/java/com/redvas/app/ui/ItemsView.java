package com.redvas.app.ui;

import com.redvas.app.items.Item;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class ItemsView extends JPanel{
    protected BufferedImage itemImage;
    protected static final BufferedImage AirFreshenerImage;
    protected static final BufferedImage CombinedTransistorImage;
    protected static final BufferedImage FFP2Image;
    protected static final BufferedImage HolyBeerImage;
    protected static final BufferedImage LogarlecImage;
    protected static final BufferedImage RottenCamembertImage;
    protected static final BufferedImage TransistorImage;
    protected static final BufferedImage TVSZImage;
    protected static final BufferedImage WetWipeImage;

    private Item item;

    public Item getItem() {
        return item;
    }

    protected ItemsView(Item item) {
        setOpaque(false);
        this.item = item;
    }

    static {
        String root = System.getProperty("user.dir");

        try {
            AirFreshenerImage = ImageIO.read(new File(root + "/src/main/resources/items/airfreshener.png"));
            CombinedTransistorImage = ImageIO.read(new File(root + "/src/main/resources/items/transistor.png"));
            FFP2Image = ImageIO.read(new File(root + "/src/main/resources/items/ffp2.png"));
            HolyBeerImage = ImageIO.read(new File(root + "/src/main/resources/items/holybeer.png"));
            LogarlecImage = ImageIO.read(new File(root + "/src/main/resources/items/logarlec.png"));
            RottenCamembertImage = ImageIO.read(new File(root + "/src/main/resources/items/rotten_camembert.png"));
            TransistorImage = ImageIO.read(new File(root + "/src/main/resources/items/transistor.png"));
            TVSZImage = ImageIO.read(new File(root + "/src/main/resources/items/tvsz.png"));
            WetWipeImage = ImageIO.read(new File(root + "/src/main/resources/items/wetwipe.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(itemImage, 0, 0, null);
    }
}


