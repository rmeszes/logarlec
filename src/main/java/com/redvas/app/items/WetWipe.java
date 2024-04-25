package com.redvas.app.items;

import com.redvas.app.players.Janitor;
import com.redvas.app.players.Player;
import com.redvas.app.players.ProximityListener;

import java.util.List;

public class WetWipe extends Item implements ProximityListener {
    /** gives protection from profs
     *
     */
    @Override
    public void use() {
        logger.fine(() -> this + " is being used...");
        super.use();
        owner.where().subscribeToProximity(this);
        whichRoom = owner.where();
        owner = null;
    }

    private void paralyzeProfessors(List<Player> players) {
        players.forEach(Player::paralyze);
    }

    @Override
    public void dispose() {
        //TODO
    }

    /**
     * @return Az Item neve
     */
    @Override
    public String toString() {
        return "Wet Wipe";
    }

    @Override
    public void proximityChanged(Player newcomer) {
        //TODO
    }

    @Override
    public void proximityEndOfRound(List<Player> proximity) {
        //TODO
    }

    @Override
    public void proximityInitially(List<Player> proximity) {
        paralyzeProfessors(proximity);
    }

    @Override
    public int listenerPriority() {
        return 0;
    }

    @Override
    public void getAffected(Janitor by) {
        logger.finest(() -> this + " getAffected(by janitor)");
    }

    @Override
    public void getAffected(AirFreshener by) {
        logger.finest(() -> this + " getAffected(by airFreshener)");
    }

    @Override
    public void affect(ProximityListener listener) {
        logger.finest(() -> this + " affect");
    }
}
