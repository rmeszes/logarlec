package com.redvas.app.ui.players;

import com.redvas.app.map.rooms.Room;
import com.redvas.app.players.Janitor;
import com.redvas.app.ui.UITool;
import com.redvas.app.ui.players.listeners.PlayerChangeListener;
import com.redvas.app.ui.rooms.RoomView;

import java.awt.*;

public class JanitorView extends PlayerView implements PlayerChangeListener {
    private Janitor janitor;
    public JanitorView(Janitor janitor) {
        super(janitor);
        this.janitor = janitor;
        this.myImage = janitorImage;
    }

    @Override
    public void faintedChanged(boolean isFainted) {
        //TODO: switch to fainted or initial image
        repaint();
    }
}
