package com.redvas.app.items;

import com.redvas.app.map.rooms.Room;
import com.redvas.app.players.Janitor;
import com.redvas.app.players.Player;
import com.redvas.app.players.ProximityListener;

import java.util.List;

public class RottenCamembert extends Item implements ProximityListener {
    public RottenCamembert(Integer id, Room whichRoom) {
        super(id, whichRoom);
    }

    protected RottenCamembert(Integer id, Player owner) {
        super(id, owner);
    }
    @Override
    public void use() {
        logger.finest(() -> this + " is being used...");
        owner.where().subscribeToProximity(this);
        whichRoom = owner.where();
        destroy();
    }

    /**
     * @return Az Item neve
     */
    @Override
    public String toString() {
        return "Cabbage Camembert";
    }

    @Override
    public void proximityChanged(Player newcomer) {
        newcomer.faint();
        logger.finest(() -> this + " proximity changed");
    }

    @Override
    public void proximityEndOfRound(List<Player> proximity) {
        knockoutEveryone(proximity);
        logger.finest(() -> this + " proximity endOfRound");
    }

    @Override
    public void proximityInitially(List<Player> proximity) {
        knockoutEveryone(proximity);
        logger.finest(() -> this + " proximityInitially");
    }

    @Override
    public int listenerPriority() {
        return 0;
    }

    @Override
    public void getAffected(Janitor by) {
        whichRoom.unsubscribeFromProximity(this);
    }

    @Override
    public void getAffected(AirFreshener by) {
        whichRoom.unsubscribeFromProximity(this);
    }

    @Override
    public void affect(ProximityListener listener) {
        //TODO
        logger.finest(() -> this + " affects" + listener);
    }

    private void knockoutEveryone(List<Player> proximity) {
        for(Player player: proximity){
            player.faint();
        }
    }
}
