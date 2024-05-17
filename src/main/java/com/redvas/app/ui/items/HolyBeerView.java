package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.HolyBeer;
import com.redvas.app.ui.ItemsView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class HolyBeerView extends ItemsView implements ItemChangeListener {
    private static final Logger logger = App.getConsoleLogger(HolyBeerView.class.getName());
    private final HolyBeer beer;
    private int x;
    private int y;
    BufferedImage beerImage;

    public HolyBeerView(HolyBeer holyBeer, int x, int y) {
        super(holyBeer);
        this.beer = holyBeer;
        this.x = x;
        this.y = y;
        this.itemImage = HolyBeerImage;
    }

    @Override
    public void positionChanged(boolean isInRoom) {

    }
}
