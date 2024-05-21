package com.redvas.app.ui.rooms;

import com.redvas.app.App;
import com.redvas.app.map.rooms.ResizingRoom;
import com.redvas.app.players.Player;

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

    BufferedImage rhFloorImage;
    BufferedImage rhFloorImageWhenGaseous;
    BufferedImage rhFloorImageWhenSticky;
    BufferedImage rhFloorImageWhenGaseousAndSticky;

    // EZT MÉG NEM TOM HOGY DÖNTI EL
    BufferedImage rvFloorImage;
    BufferedImage rvFloorImageWhenGaseous;
    BufferedImage rvFloorImageWhenSticky;
    BufferedImage rvFloorImageWhenGaseousAndSticky;

    public ResizingRoomView(ResizingRoom rr, int x, int y) {
        this.rRoom = rr;
        this.x = x;
        this.y = y;
        try {
            rhFloorImage = ImageIO.read(new File("src/main/resources/map/horizontal_room.png"));
            rhFloorImageWhenGaseous = ImageIO.read(new File("src/main/resources/map/horizontal_gaseous_room.png"));
            rhFloorImageWhenSticky = ImageIO.read(new File("src/main/resources/map/horizontal_sticky_room.png"));
            rhFloorImageWhenGaseousAndSticky = ImageIO.read(new File("src/main/resources/map/horizontal_sticky_gaseous_room.png"));
            rvFloorImage = ImageIO.read(new File("src/main/resources/map/vertical_room.png"));
            rvFloorImageWhenGaseous = ImageIO.read(new File("src/main/resources/map/vertical_gaseous_room.png"));
            rvFloorImageWhenSticky = ImageIO.read(new File("src/main/resources/map/vertical_sticky_room.png"));
            rvFloorImageWhenGaseousAndSticky = ImageIO.read(new File("src/main/resources/map/vertical_sticky_gaseous_room.png"));
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

    @Override
    public void occupantLeft(Player p) {

    }

    @Override
    public void occupantEntered(Player p) {

    }

    public void draw(Graphics2D g) {
        int roomWidth = 200;
        int roomHeight = 200;
        g.setColor(Color.BLACK);

        if (!isGaseous && !isSticky) {      // alap
            g.drawImage(rhFloorImage, x, y, roomWidth, roomHeight, null);
        }

        else if (!isGaseous) {  // csak ragad
            g.drawImage(rhFloorImageWhenSticky, x, y, roomWidth, roomHeight, null);
        }

        else if (!isSticky) {  // csak gázos
            g.drawImage(rhFloorImageWhenGaseous, x, y, roomWidth, roomHeight, null);
        }

        else  {  // gázos és ragad
            g.drawImage(rhFloorImageWhenGaseousAndSticky, x, y, roomWidth, roomHeight, null);
        }
    }
}
