package com.redvas.app.ui.rooms;

import com.redvas.app.App;
import com.redvas.app.map.Direction;
import com.redvas.app.map.rooms.ResizingRoom;
import com.redvas.app.players.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class ResizingRoomView extends RoomView implements ResizingRoomChangeListener {
    private static final Logger logger = App.getConsoleLogger(ResizingRoomView.class.getName());

    private ResizingRoom rr;
    public ResizingRoomView(ResizingRoom rr, int x, int y) {
        super(rr, x, y, false);
        rr.setListener((ResizingRoomChangeListener) this);
        originalX = x;
        originalY = y;
        this.rr = rr;
        updateImage();
    }
    private int originalX, originalY;

    @Override
    protected void updateImage() {
        if (rr.isMerged()) {
            RoomView toIncorporate = null;

            for (DoorView dv : doors)
                if ((toIncorporate = (RoomView) dv.getDoor().connectsTo(rr.getMergeDirection()).getListener()) != null)
                    break;

            if (rr.getMergeDirection() == Direction.UP || rr.getMergeDirection() == Direction.DOWN) {
                if (rr.getMergeDirection() == Direction.UP && toIncorporate != null)
                    setBounds(toIncorporate.getBounds().x, toIncorporate.getBounds().y, RoomView.SIZE, RoomView.SIZE * 2);
                else
                    setBounds(originalX, originalY, RoomView.SIZE, RoomView.SIZE * 2);

                if (isSticky) {
                    if (isGaseous) myImage = verticalStickyGaseous;
                    else myImage = verticalSticky;
                }
                else {
                    if (isGaseous) myImage = verticalGaseous;
                    else myImage = vertical;
                }
            }
            else {
                if (rr.getMergeDirection() == Direction.LEFT && toIncorporate != null)
                    setBounds(originalX - toIncorporate.getBounds().x, originalY, RoomView.SIZE * 2, RoomView.SIZE);
                else
                    setBounds(originalX, originalY, (int)getBounds().getWidth() * 2, RoomView.SIZE);

                super.updateImage();
            }
        }
        else {
            super.updateImage();
        }

        repaintCorrectly();
    }


    @Override
    public void mergedChanged(boolean isMerged) {
        updateImage();
    }
}
