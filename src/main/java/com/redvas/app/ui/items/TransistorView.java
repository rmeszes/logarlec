package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.Transistor;
import com.redvas.app.ui.ItemsView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class TransistorView extends ItemsView implements ItemChangeListener {
    private static final Logger logger = App.getConsoleLogger(TransistorView.class.getName());
    private final Transistor transistor;
    private int x;
    private int y;
    BufferedImage transistorImage;

    public TransistorView(Transistor t, int x, int y) {
        super(t);
        this.transistor = t;
        this.x = x;
        this.y = y;
        this.itemImage = TransistorImage;
    }


    @Override
    public void positionChanged(boolean isInRoom) {

    }
}
