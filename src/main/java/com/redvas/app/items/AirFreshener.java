package com.redvas.app.items;

import com.redvas.app.players.Janitor;
import com.redvas.app.players.Player;
import com.redvas.app.players.ProximityListener;

import java.util.List;

public class AirFreshener implements ProximityListener {
    public void use() {

    }

    public void proximityChanged(Player newcomer) {}
    public void proximityEndOfRound(List<Player> proximity) {}
    public void proximityInitially(List<Player> proximity) {}
    public int listenerPriority() {
        return 1;
    }
    public void getAffected(Janitor by) {}
    public void getAffected(AirFreshener by) {}

    @Override
    public void affect(ProximityListener listener) {
        listener.getAffected(this);
    }

}
