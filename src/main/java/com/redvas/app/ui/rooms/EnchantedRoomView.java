package com.redvas.app.ui.rooms;

import com.redvas.app.App;
import com.redvas.app.map.rooms.EnchantedRoom;
import com.redvas.app.ui.View;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class EnchantedRoomView implements RoomChangeListener, View {
    private static final Logger logger = App.getConsoleLogger(EnchantedRoomView.class.getName());

    private final EnchantedRoom eRoom;
    private boolean isSticky = false;
    private boolean isGaseous = false;

    BufferedImage eFloorImage;
    BufferedImage eFloorImageWhenGaseous;
    BufferedImage eFloorImageWhenSticky;
    BufferedImage eFloorImageWhenGaseousAndSticky;
private int x, y;
    public EnchantedRoomView(EnchantedRoom er, int x, int y) {
        this.eRoom = er;
        this.x = x;
        this.y = y;
        try {
            eFloorImage = ImageIO.read(new File("src/main/resources/floor.png"));
            eFloorImageWhenGaseous = ImageIO.read(new File("src/main/resources/floor.png"));
            eFloorImageWhenSticky = ImageIO.read(new File("src/main/resources/floor.png"));
            eFloorImageWhenGaseousAndSticky = ImageIO.read(new File("src/main/resources/floor.png"));
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }

    @Override
    public void roomStickinessChange(boolean isSticky) {
        this.isSticky = isSticky;
    }

    @Override
    public void roomGaseousnessChange(boolean isGaseous) {
        this.isGaseous = isGaseous;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        int roomWidth = 200;
        int roomHeight = 200;

        if (!isGaseous && !isSticky) {      // alap
            g.drawImage(eFloorImage, x, y, roomWidth, roomHeight, null);
        }

        else if (!isGaseous) {  // csak ragad
            g.drawImage(eFloorImageWhenSticky, x, y, roomWidth, roomHeight, null);
        }

        else if (!isSticky) {  // csak gázos
            g.drawImage(eFloorImageWhenGaseous, x, y, roomWidth, roomHeight, null);
        }

        else  {  // gázos és ragad
            g.drawImage(eFloorImageWhenGaseousAndSticky, x, y, roomWidth, roomHeight, null);
        }
    }

    @Override
    public void draw() {

    }
}
