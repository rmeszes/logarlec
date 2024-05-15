package com.redvas.app.ui.players.listeners;

import com.redvas.app.map.rooms.Room;

public interface PlayerChangeListener {
    // TODO: ACKNOWLEDGE IT DIDNT MAKE SENSE
    // void positionChanged(Room from, Room to);
    void faintedChanged(boolean isFainted);
}
