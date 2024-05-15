package com.redvas.app.ui.rooms;

import com.redvas.app.players.Player;

public interface RoomChangeListener {
    void roomStickinessChange(boolean isSticky);
    void roomGaseousnessChange(boolean isGaseous);
    void occupantLeft(Player p);
    void occupantEntered(Player p);
}
