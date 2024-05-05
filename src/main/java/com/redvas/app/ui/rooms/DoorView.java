package com.redvas.app.ui.rooms;

import com.redvas.app.App;
import com.redvas.app.map.Door;
import com.redvas.app.map.rooms.EnchantedRoom;

import java.awt.image.BufferedImage;
import java.util.logging.Logger;

public class DoorView {

    private static final Logger logger = App.getConsoleLogger(EnchantedRoomView.class.getName());

    private final Door door;
    private final int x;
    private final int y;

    public DoorView(Door door, int x, int y) {

        this.door = door;
        this.x = x;
        this.y = y;
    }
}
