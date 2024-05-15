package com.redvas.app.ui.players;

import com.redvas.app.map.rooms.Room;
import com.redvas.app.players.Undergraduate;
import com.redvas.app.ui.UITool;
import com.redvas.app.ui.players.listeners.PlayerChangeListener;
import com.redvas.app.ui.players.listeners.UndergraduateChangeListener;
import com.redvas.app.ui.rooms.RoomView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class UndergraduateView extends PlayerView implements PlayerChangeListener, UndergraduateChangeListener {
    private final Undergraduate undergraduate;
    public UndergraduateView(Undergraduate undergraduate) {
        super(undergraduate);
        this.undergraduate = undergraduate;
        this.myImage = undergraduateImage;
        this.undergraduate.setListener((UndergraduateChangeListener) this);
    }

    @Override
    public void droppedOut() {
        // TODO: switch to dropped out or initial image
        repaint();
    }

    @Override
    public void faintedChanged(boolean isFainted) {
        // TODO: switch to fainted or initial image
        repaint();
    }
}
