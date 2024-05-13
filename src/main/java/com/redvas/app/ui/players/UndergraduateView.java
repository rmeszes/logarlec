package com.redvas.app.ui.players;

import com.redvas.app.map.rooms.Room;
import com.redvas.app.players.Undergraduate;
import com.redvas.app.ui.players.listeners.PlayerChangeListener;
import com.redvas.app.ui.players.listeners.UndergraduateChangeListener;

public class UndergraduateView implements PlayerChangeListener, UndergraduateChangeListener {
    private final Undergraduate undergraduate;
    public UndergraduateView(Undergraduate undergraduate) {
        this.undergraduate = undergraduate;
    }

    @Override
    public void droppedOut() {

    }

    @Override
    public void positionChanged(Room from, Room to) {

    }

    @Override
    public void faintedChanged(boolean isFainted) {

    }
}
