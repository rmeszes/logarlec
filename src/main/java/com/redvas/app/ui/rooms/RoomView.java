package com.redvas.app.ui.rooms;

import com.redvas.app.App;
import com.redvas.app.map.rooms.EnchantedRoom;
import com.redvas.app.map.rooms.Room;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class RoomView implements RoomChangeListener {
    private static final Logger logger = App.getConsoleLogger(RoomView.class.getName());

    private final Room room;
    private final int x, y;
    private boolean isSticky = false;       // alapból legyen hamis, de am ez nem int a modellben??
    private boolean isGaseous = false;

    BufferedImage floorImage;               // alap
    BufferedImage floorImageWhenGaseous;    // amikor gázossá válik
    BufferedImage floorImageWhenSticky;     // amikor ragadós a szoba
    BufferedImage floorImageWhenGaseousAndSticky;     // amikor ragadós és gázos a szoba   VAN ILYEN??

    public RoomView(Room r, int x, int y) {
        this.room = r;
        this.x = x;
        this.y = y;
        try {
            floorImage = ImageIO.read(new File("src/main/resources/floor.png"));
            floorImageWhenGaseous = ImageIO.read(new File("src/main/resources/floor.png"));
            floorImageWhenSticky = ImageIO.read(new File("src/main/resources/floor.png"));
            floorImageWhenGaseousAndSticky = ImageIO.read(new File("src/main/resources/floor.png"));
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }

    @Override
    public void roomStickinessChange(boolean _isSticky) {    // Amikor a modellben változik, akkor ezt kell hívni és ez updateli a view-t
        isSticky = _isSticky;

        // redraw majd itt ?    De ugye nem komponens, a tartalmazó cucc repaintj-je??
    }

    @Override
    public void roomGaseousnessChange(boolean _isGaseous) {
        isGaseous = _isGaseous;

    }

    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        int roomWidth = 200;
        int roomHeight = 200;

        if (!isGaseous && !isSticky) {      // alap
            g.drawImage(floorImage, x, y, roomWidth, roomHeight, null);
        }

        else if (!isGaseous) {  // csak ragad
            g.drawImage(floorImageWhenSticky, x, y, roomWidth, roomHeight, null);
        }

        else if (!isSticky) {  // csak gázos
            g.drawImage(floorImageWhenGaseous, x, y, roomWidth, roomHeight, null);
        }

        else  {  // gázos és ragad
            g.drawImage(floorImageWhenGaseousAndSticky, x, y, roomWidth, roomHeight, null);
        }


    }
}
