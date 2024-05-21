package com.redvas.app.ui.rooms;

import com.redvas.app.App;
import com.redvas.app.map.rooms.EnchantedRoom;
import com.redvas.app.players.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class EnchantedRoomView extends RoomView implements RoomChangeListener {
    private static final Logger logger = App.getConsoleLogger(EnchantedRoomView.class.getName());

    private EnchantedRoom er;

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

    @Override
    public void roomStickinessChange(boolean isSticky) {
        this.isSticky = isSticky;
    }

    @Override
    public void roomGaseousnessChange(boolean isGaseous) {
        this.isGaseous = isGaseous;
    }
}
