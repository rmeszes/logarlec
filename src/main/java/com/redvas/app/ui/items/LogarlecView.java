package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.Logarlec;
import com.redvas.app.ui.ItemsView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class LogarlecView extends ItemsView implements ItemChangeListener {
    private static final Logger logger = App.getConsoleLogger(LogarlecView.class.getName());
    private final Logarlec logarlec;
    private int x;
    private int y;
    BufferedImage logarlecImage;

    public LogarlecView(Logarlec l, int x, int y) {
        super(l);
        this.logarlec = l;
        this.x = x;
        this.y = y;
        this.itemImage = LogarlecImage;
    }

    @Override
    public void positionChanged(boolean isInRoom) {

    }

}
