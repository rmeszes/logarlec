package com.redvas.app.items;

import com.redvas.app.players.Janitor;
import com.redvas.app.players.Player;
import com.redvas.app.players.ProximityListener;

import java.util.List;

public class RottenCamembert extends Item implements ProximityListener {
    /** sets the gas of the room to true
     *
     */
    @Override
    public void use() {
        logger.finest(() -> this + " is being used...");
        super.use();
        owner.where().subscribeToProximity(this);
        whichRoom = owner.where();
        owner = null;
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
        //TODO
        logger.finest(() -> this + " proximity endOfRound");
    }

    @Override
    public void proximityInitially(List<Player> proximity) {
        for(Player player: proximity){
            player.faint();
        }
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
}
