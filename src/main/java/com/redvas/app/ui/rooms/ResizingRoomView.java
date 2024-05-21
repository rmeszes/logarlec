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

public class ResizingRoomView extends RoomView implements RoomChangeListener {
    private static final Logger logger = App.getConsoleLogger(ResizingRoomView.class.getName());
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

    private ResizingRoom rr;
    public ResizingRoomView(ResizingRoom rr, int x, int y) {
        super(rr, x, y);
        this.rr = rr;
        updateImage();
    }

    @Override
    protected void updateImage() {
        if (rr.isMerged()) {

        }
        else {
            if (isSticky) {
                if (isGaseous) myImage = basicStickyGaseous;
                else myImage = basicSticky;
            }
            else {
                if (isGaseous) myImage = basicGaseous;
                else myImage = basic;
            }
        }


        repaintCorrectly();
    }
}
