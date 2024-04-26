package com.redvas.app.players;

import com.redvas.app.App;
import com.redvas.app.Game;
import com.redvas.app.items.AirFreshener;
import com.redvas.app.map.Room;

import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Professor extends Player implements ProximityListener {
    protected static final Logger logger = App.getConsoleLogger(Professor.class.getName());
    private int paralyzeCountdown;
    public Professor(Room room, Game game) {
        super(room, game);
        paralyzeCountdown = 0;
    }

    /**
     *
     * @param index: identifier of item that will be used
     */
    @Override
    protected boolean useItem(int index) {
        // Nothing happens
        return false;        // itt valamiért true volt
    }



    private void dropoutUndergraduates() {
        for (Player p : super.where().getOccupants()) {
           p.dropout();
        }

    }


    /** they can not win the game
     *
     */
    @Override
    public void pickLogarlec() {
        // Nothing happens
    }

    @Override
    public void step() {
        if(paralyzeCountdown != 0) {
            paralyzeCountdown--;    // itt returnol
        }
        else {
            super.step();
        }
    }

    /** they stop moving and causing undergrads to drop out
     *
     */
    @Override
    public void paralyze() {
        paralyzeCountdown = 3;
    }

    /** only undergrads can
     *
     */
    @Override
    public void dropout() {
        // Nothing happens
    }

    @Override
    public String toString() {
        return "Professor";
    }

    @Override
    public void proximityChanged(Player newcomer) {
        logger.finest(()-> this + " proximity changed");
    }

    @Override
    public void proximityEndOfRound(List<Player> proximity) {
        logger.finest(() -> this + " proximity endofround");
    }

    @Override
    public void proximityInitially(List<Player> proximity) {
        logger.finest(() -> this + " is proximity initially");
    }

    @Override
    public int listenerPriority() {
        return 0;
    }

    @Override
    public void getAffected(Janitor by) {
        logger.finest(() -> by + " getAffected(janitor)");
    }

    @Override
    public void getAffected(AirFreshener by) {
        logger.finest(() -> this + " getAffected(airfreshener)");
    }

    @Override
    public void affect(ProximityListener listener) {
        logger.finest(() -> this + " affect()");
    }
}
