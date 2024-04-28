package com.redvas.app.items;

import com.redvas.app.map.rooms.Room;
import com.redvas.app.players.Janitor;
import com.redvas.app.players.Player;
import com.redvas.app.players.ProximityListener;

import java.util.List;

public class AirFreshener extends Item implements ProximityListener {

    protected AirFreshener(Integer id, Player owner) {
        super(id, owner);
    }
    public AirFreshener(Integer id, Room whichRoom) {
        super(id, whichRoom);
    }

    @Override
    public void use() {
        logger.finest("use");
        super.use();
        owner.where().subscribeToProximity(this);
        owner.where().unsubscribeFromProximity(this);
    }

    @Override
    public String toString() {
        return "AirFreshener";
    }

    public void proximityChanged(Player newcomer) {
        logger.finest("proximityChanged");
    }
    public void proximityEndOfRound(List<Player> proximity) {
        logger.finest("proximityEndOfRound");
    }
    public void proximityInitially(List<Player> proximity) {
        logger.finest("proximityInitially");
    }
    public int listenerPriority() {
        return 4;
    }
    public void getAffected(Janitor by) {
        logger.finest("getAffected");
    }
    public void getAffected(AirFreshener by) {
        logger.finest("getAffected");
    }

    @Override
    public void affect(ProximityListener listener) {
        listener.getAffected(this);
    }

}
