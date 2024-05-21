package com.redvas.app.ui.items;

import com.redvas.app.items.Transistor;

import java.awt.image.BufferedImage;

public class TransistorView extends ItemsView implements ItemChangeListener {
    private final Transistor transistor;
    BufferedImage transistorImage;

    public TransistorView(Transistor t) {
        super(t);
        this.transistor = t;
        this.itemImage = TransistorImage;
        t.setListener(this);
    }
}
