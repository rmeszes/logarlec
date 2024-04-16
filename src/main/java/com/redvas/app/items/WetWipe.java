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
        owner().where().paralyzeProfessors();
        destroy();
    }

    private void paralyzeProfessors() {

    }

    @Override
    public void dispose() {

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

    }

    @Override
    public void proximityEndOfRound(List<Player> proximity) {

    }

    @Override
    public void proximityInitially(List<Player> proximity) {

    }

    @Override
    public int listenerPriority() {
        return 0;
    }

    @Override
    public void getAffected(Janitor by) {

    }

    @Override
    public void getAffected(AirFreshener by) {

    }
}
