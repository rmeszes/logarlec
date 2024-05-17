package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.RottenCamembert;
import com.redvas.app.ui.ItemsView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class RottenCamembertView extends ItemsView implements ItemChangeListener {
    private static final Logger logger = App.getConsoleLogger(RottenCamembertView.class.getName());
    private final RottenCamembert camembert;
    private int x;
    private int y;

    public RottenCamembertView(RottenCamembert rottenCamembert, int x, int y) {
        super(rottenCamembert);
        this.camembert = rottenCamembert;
        this.x = x;
        this.y = y;
        this.itemImage = RottenCamembertImage;
    }

    @Override
    public void positionChanged(boolean isInRoom) {

    }

}
