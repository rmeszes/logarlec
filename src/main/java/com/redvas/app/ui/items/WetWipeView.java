package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.WetWipe;
import com.redvas.app.ui.ItemsView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class WetWipeView extends ItemsView implements ItemChangeListener {
    private static final Logger logger = App.getConsoleLogger(WetWipeView.class.getName());
    private final WetWipe wipe;
    private int x;
    private int y;

    public WetWipeView(WetWipe wetWipe, int x, int y) {
        super(wetWipe);
        this.wipe = wetWipe;
        this.x = x;
        this.y = y;
        this.itemImage = WetWipeImage;
    }


    @Override
    public void positionChanged(boolean isInRoom) {

    }
}
