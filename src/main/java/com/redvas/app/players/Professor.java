package com.redvas.app.players;

import com.redvas.app.App;
import com.redvas.app.Game;
import com.redvas.app.items.AirFreshener;
import com.redvas.app.map.rooms.Room;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Professor extends Player implements ProximityListener {
    protected static final Logger logger = App.getConsoleLogger(Professor.class.getName());
    private int paralyzeCountdown;

    public Professor(Integer id, Room room, Game game) {
        super(id, room, game);
        paralyzeCountdown = 0;
        where.subscribeToProximity(this);
        where.addOccupant(this);
    }

    @Override
    public void loadXML(Element professor) {
        super.loadXML(professor);
        paralyzeCountdown = Integer.parseInt(professor.getAttribute("paralyze_countdown"));
        where.subscribeToProximity(this);
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
        return false;        // itt valamiért true volt
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
            Room room = randomMove();
            if(room != null) {
                room.subscribeToProximity(this);
            }
        }

    }

    /** they stop moving and causing undergrads to drop out
     * until the countdown is not 0
     */
    @Override
    public void paralyze() {
        paralyzeCountdown = 3;
        logger.finest(() -> this + " is paralyzed");
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
        List<Player> players = new ArrayList<>();
        players.add(newcomer);
        dropoutUndergraduates(players);
    }

    @Override
    public void proximityEndOfRound(List<Player> proximity) {
        dropoutUndergraduates(proximity);
    }

    @Override
    public void proximityInitially(List<Player> proximity) {
        dropoutUndergraduates(proximity);
    }

    @Override
    public int listenerPriority() {
        return 0;
    }

    @Override
    public void getAffected(Janitor by) {
        //don't think its needed (unless more complicated solution for janitor)
    }

    @Override
    public void getAffected(AirFreshener by) {
        ////don't think its needed
    }

    @Override
    public void affect(ProximityListener listener) {
        //does nothing
    }

    private void dropoutUndergraduates(List<Player> proximity) {
        for(Player player : proximity) {
            player.dropout();
        }
    }

    @Override
    public Element savePhantomListenerXML(Document document) {
        return null;
    }
}
