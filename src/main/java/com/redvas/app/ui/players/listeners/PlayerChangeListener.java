package com.redvas.app.ui.players.listeners;

import com.redvas.app.map.rooms.Room;

public interface PlayerChangeListener {
    void positionChanged(Room from, Room to);
    void faintedChanged(boolean isFainted);
}
