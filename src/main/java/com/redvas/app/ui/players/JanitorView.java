package com.redvas.app.ui.players;

import com.redvas.app.map.rooms.Room;
import com.redvas.app.players.Janitor;
import com.redvas.app.ui.players.listeners.PlayerChangeListener;

public class JanitorView implements PlayerChangeListener {
    private Janitor janitor;
    public JanitorView(Janitor janitor) {
        this.janitor = janitor;
    }

    @Override
    public void positionChanged(Room from, Room to) {

    }

    @Override
    public void faintedChanged(boolean isFainted) {

    }
}
