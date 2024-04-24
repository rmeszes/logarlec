package com.redvas.app.players;

import com.redvas.app.Game;
import com.redvas.app.items.AirFreshener;
import com.redvas.app.map.Room;

import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Professor extends Player implements ProximityListener {
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
        return true;
    }

    protected static final Logger logger = Logger.getLogger("Professor");

    static {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINEST);
        logger.addHandler(handler);
        logger.setLevel(Level.FINEST);
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

    }

    /** they stop moving and causing undergrads to drop out
     *
     */
    @Override
    public void paralyze() {
        logger.fine(() -> this + " is paralyzed");
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

    @Override
    public void affect(ProximityListener listener) {

    }
}
