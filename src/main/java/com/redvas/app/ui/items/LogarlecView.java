package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.Logarlec;
import com.redvas.app.ui.ItemsView;

import java.awt.image.BufferedImage;
import java.util.logging.Logger;

public class LogarlecView extends ItemsView implements ItemChangeListener {
    private static final Logger logger = App.getConsoleLogger(LogarlecView.class.getName());
    private final Logarlec logarlec;
    private int x;
    private int y;

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
