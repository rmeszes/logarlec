package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.Transistor;

import java.awt.image.BufferedImage;
import java.util.logging.Logger;

public class TransistorView extends ItemsView implements ItemChangeListener {
    private final Transistor transistor;
    BufferedImage transistorImage;

    public TransistorView(Transistor t) {
        super(t);
        this.transistor = t;
        this.itemImage = TransistorImage;
    }


    @Override
    public void isInRoom(boolean isInRoom) {
        if (isInRoom)
            this.setOpaque(true);
        else
            this.setOpaque(false);
    }
}
