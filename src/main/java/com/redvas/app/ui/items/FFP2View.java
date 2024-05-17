package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.FFP2;
import com.redvas.app.ui.ItemsView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class FFP2View extends ItemsView implements ItemChangeListener {
    private static final Logger logger = App.getConsoleLogger(FFP2View.class.getName());
    private final FFP2 ffp2;
    private int x;
    private int y;

    public FFP2View(FFP2 ffp2, int x, int y) {
        super(ffp2);
        this.ffp2 = ffp2;
        this.x = x;
        this.y = y;
        this.itemImage = FFP2Image;
    }


    @Override
    public void positionChanged(boolean isInRoom) {

    }
}
