package com.redvas.app.players;

import com.redvas.app.items.AirFreshener;

import java.util.List;

public interface ProximityListener {
    public void proximityChanged(Player newcomer);
    public void proximityEndOfRound(List<Player> proximity);
    public void proximityInitially(List<Player> proximity);
    public int listenerPriority();
    public void getAffected(Janitor by);
    public void getAffected(AirFreshener by);
}
