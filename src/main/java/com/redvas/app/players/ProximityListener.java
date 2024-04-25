package com.redvas.app.players;

import com.redvas.app.items.AirFreshener;

import java.util.List;

public interface ProximityListener {
    void proximityChanged(Player newcomer);
    void proximityEndOfRound(List<Player> proximity);
    void proximityInitially(List<Player> proximity);
    int listenerPriority();
    void getAffected(Janitor by);
    void getAffected(AirFreshener by);
    void affect(ProximityListener listener);
}
