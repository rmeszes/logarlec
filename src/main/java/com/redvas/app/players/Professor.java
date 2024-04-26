package com.redvas.app.players;

import com.redvas.app.App;
import com.redvas.app.Game;
import com.redvas.app.items.AirFreshener;
import com.redvas.app.map.Rooms.Room;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;
import java.util.logging.Logger;

public class Professor extends Player implements ProximityListener {
    private int paralyzeCountdown;
    public Professor(int id, Room room, Game game) {
        super(id, room, game);
        paralyzeCountdown = 0;
    }

    @Override
    public void moveTo(Room room) {
        if (where() != null)
            where().unsubscribeFromProximity(this);
        room.subscribeToProximity(this);
        super.moveTo(room);
    }

    @Override
    public Element saveXML(Document document) {
        Element professor = super.saveXML(document);
        professor.setAttribute("paralyze_countdown", String.valueOf(paralyzeCountdown));
        return professor;
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

    protected static final Logger logger = App.getConsoleLogger(Professor.class.getName());

    /** they can not win the game
     *
     */
    @Override
    public void pickLogarlec() {
        // Nothing happens
    }

    @Override
    public void step() {
        logger.finest(()-> this + "step");
    }

    /** they stop moving and causing undergrads to drop out
     * until the countdown is not 0
     */
    @Override
    public void paralyze() {

        logger.fine(() -> this + " is paralyzed");
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
