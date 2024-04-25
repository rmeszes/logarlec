package com.redvas.app.items;

import com.redvas.app.players.Janitor;
import com.redvas.app.players.Player;
import com.redvas.app.players.ProximityListener;

import java.util.List;

public class WetWipe extends Item implements ProximityListener {

    /** gives protection from profs FOR 5 rounds
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

    /** meghivasakor 5-re allitja a maradando korok szamat amig meg le van benulva a prof
     *
     * @param players
     */
    private void paralyzeProfessors(List<Player> players) {
        for (Player p : players) {
            p.paralyze();
            p.setParalyzeCountdown(5);
        }
    }

    /** removes from owners inventory
     * then puts it on the floor of the room
     *
     */
    @Override
    public void dispose() {
        owner.removeFromInventory(this);
        whichRoom.addItem(this);
        owner = null;
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
        newcomer.paralyze();
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
        //It does nothing, Janitor DOES NOT affect the wetwipe
        logger.finest(() -> this + " getAffected(by janitor)");
    }

    @Override
    public void getAffected(AirFreshener by) {
        //It does nothing, AirFreshener DOES NOT affect the wetwipe
        logger.finest(() -> this + " getAffected(by airFreshener)");
    }

    @Override
    public void affect(ProximityListener listener) {
        //TODO
        logger.finest(() -> this + " affect");
    }
}
