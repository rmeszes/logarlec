package com.redvas.app.ui.rooms;

import com.redvas.app.App;
import com.redvas.app.map.rooms.ResizingRoom;
import com.redvas.app.map.rooms.Room;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class ResizingRoomView implements RoomChangeListener {
    private static final Logger logger = App.getConsoleLogger(ResizingRoomView.class.getName());

    private final ResizingRoom rRoom;
    private final int x;
    private final int y;
    private boolean isSticky = false;       // alapból legyen hamis, de am ez nem int a modellben??
    private boolean isGaseous = false;

    BufferedImage rFloorImage;
    BufferedImage rFloorImageWhenGaseous;
    BufferedImage rFloorImageWhenSticky;
    BufferedImage rFloorImageWhenGaseousAndSticky;

    public ResizingRoomView(ResizingRoom rr, int x, int y) {
        this.rRoom = rr;
        this.x = x;
        this.y = y;
        try {
            rFloorImage = ImageIO.read(new File("src/main/resources/floor.png"));
            rFloorImageWhenGaseous = ImageIO.read(new File("src/main/resources/floor.png"));
            rFloorImageWhenSticky = ImageIO.read(new File("src/main/resources/floor.png"));
            rFloorImageWhenGaseousAndSticky = ImageIO.read(new File("src/main/resources/floor.png"));
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }

    @Override
    public void roomStickinessChange(boolean isSticky) {
        this.isSticky = isSticky;
        return;
    }

    @Override
    public void roomGaseousnessChange(boolean isGaseous) {
        this.isGaseous = isGaseous;
        return;
    }

    public void draw(Graphics2D g) {
        int roomWidth = 200;
        int roomHeight = 200;
        g.setColor(Color.BLACK);

        if (!isGaseous && !isSticky) {      // alap
            g.drawImage(rFloorImage, x, y, roomWidth, roomHeight, null);
        }

        else if (!isGaseous) {  // csak ragad
            g.drawImage(rFloorImageWhenSticky, x, y, roomWidth, roomHeight, null);
        }

        else if (!isSticky) {  // csak gázos
            g.drawImage(rFloorImageWhenGaseous, x, y, roomWidth, roomHeight, null);
        }

        else  {  // gázos és ragad
            g.drawImage(rFloorImageWhenGaseousAndSticky, x, y, roomWidth, roomHeight, null);
        }


    }
}
