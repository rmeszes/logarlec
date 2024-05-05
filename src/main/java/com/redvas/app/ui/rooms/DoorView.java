package com.redvas.app.ui.rooms;

import com.redvas.app.App;
import com.redvas.app.map.Door;
import com.redvas.app.map.rooms.EnchantedRoom;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class DoorView {

    private static final Logger logger = App.getConsoleLogger(EnchantedRoomView.class.getName());

    private final Door door;
    private final int x;
    private final int y;
    BufferedImage doorImage;

    public DoorView(Door door, int x, int y) {

        this.door = door;
        this.x = x;
        this.y = y;
        try {
            doorImage = ImageIO.read(new File("src/main/resources/floor.png"));
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }
    public void draw(Graphics2D g) {

    }
}
