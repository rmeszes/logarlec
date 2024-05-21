package com.redvas.app.ui.rooms;

import com.redvas.app.App;
import com.redvas.app.map.rooms.EnchantedRoom;

import java.util.logging.Logger;

public class EnchantedRoomView extends RoomView implements RoomChangeListener {
    private static final Logger logger = App.getConsoleLogger(EnchantedRoomView.class.getName());

    private final transient EnchantedRoom er;

    public EnchantedRoomView(EnchantedRoom er, int x, int y) {
        super(er, x, y);
        this.er = er;
    }

    @Override
    protected void updateImage() {
        if (isSticky) {
            if (isGaseous) myImage = enchantedStickyGaseous;
            else myImage = enchantedSticky;
        }
        else {
            if (isGaseous) myImage = enchantedGaseous;
            else myImage = enchanted;
        }

        repaintCorrectly();
    }
}
