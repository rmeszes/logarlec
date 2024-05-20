package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.HolyBeer;

import java.awt.image.BufferedImage;
import java.util.logging.Logger;

public class HolyBeerView extends ItemsView implements ItemChangeListener {
    private final HolyBeer beer;

    public HolyBeerView(HolyBeer holyBeer) {
        super(holyBeer);
        this.beer = holyBeer;
        this.itemImage = HolyBeerImage;
    }

    @Override
    public void isInRoom(boolean isInRoom) {
        if (isInRoom)
            this.setOpaque(true);
        else
            this.setOpaque(false);
        repaint();
    }
}
