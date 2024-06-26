package com.redvas.app.players;

import com.redvas.app.App;
import com.redvas.app.Game;
import com.redvas.app.items.AirFreshener;
import com.redvas.app.map.rooms.Room;
import com.redvas.app.ui.players.listeners.ProfessorChangeListener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Professor extends Player implements ProximityListener{
    protected static final Logger logger = App.getConsoleLogger(Professor.class.getName());
    private int paralyzeCountdown;

    /**
     *
     * @param id: identificator of this sepcific prof
     * @param room: where they are
     * @param game: which game they are in
     */
    public Professor(Integer id, Room room, Game game) {
        super(id, room, game);
        paralyzeCountdown = 0;
        where.subscribeToProximity(this);
        // where.addOccupant(this);
    }

    private ProfessorChangeListener listener = null;

    public void setListener(ProfessorChangeListener listener) {
        this.listener = listener;
    }

    /**
     *
     * @param professor: the instance of a professor that we are loading
     */
    @Override
    public void loadXML(Element professor) {
        super.loadXML(professor);
        paralyzeCountdown = Integer.parseInt(professor.getAttribute("paralyze_countdown"));
        where.subscribeToProximity(this);
    }

    /**
     * they unsubscribe from the proximity of the room they are leaving
     * and subscribe to the one where they enter
     * this will make them affected by WetWipes, gas etc
     * @param room: chosen room where they move (they move randomly)
     */
    @Override
    public void moveTo(Room room) {
        if (where() != null)
            where().unsubscribeFromProximity(this);
        room.subscribeToProximity(this);
        super.moveTo(room);

    }

    /**
     *
     * @param document: xml file where we save the current state of the game
     * @return
     */
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

    /**
     * if they are not paralyzed they choose a random room
     */
    @Override
    public void step() {
        if(paralyzeCountdown != 0) {
            paralyzeCountdown--;    // itt returnol

            if (paralyzeCountdown == 0 && listener != null)
                listener.paralyzedChanged(false);
        }
        else if (faintCountdown == 0)  {
            Room room = randomMove();

            if(room != null) {

                room.subscribeToProximity(this);
                moveTo(room);
            }
        }

        super.step();
    }

    /** they stop moving and causing undergrads to drop out
     * until the countdown is not 0
     */
    @Override
    public void paralyze() {
        paralyzeCountdown = 3;
        if (listener != null)
            listener.paralyzedChanged(true);
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

    /**
     * if they are unprotected, they drop out
     *
     */
    @Override
    public void proximityChanged(Player newcomer) {
        List<Player> players = new ArrayList<>();
        players.add(newcomer);
        dropoutUndergraduates(players);
    }

    /**
     *
     * @param proximity: list of players that are in the room by the end of the round
     *                 if they are unprotected, need to be dropped out
     *                 the undergrad.dropout() function checks whether they are protected or not
     */
    @Override
    public void proximityEndOfRound(List<Player> proximity) {
        dropoutUndergraduates(proximity);
    }
    /**
     *
     * @param proximity: list of players that are in the room by the beginning of the round
     *
     */
    @Override
    public void proximityInitially(List<Player> proximity) {
        dropoutUndergraduates(proximity);
    }

    @Override
    public int listenerPriority() {
        return 0;
    }

    /**
     * Professors are not affected by janitors
     *
     */
    @Override
    public void getAffected(Janitor by) {
        return;
    }
    /**
     * not affected by airfresheners
     *
     */
    @Override
    public void getAffected(AirFreshener by) {
        return;
    }

    /**
     * must implement the interface's functions
     * @param listener
     */
    @Override
    public void affect(ProximityListener listener) {
        return;
    }

    /**
     *
     * @param proximity: players in this room
     *                 the dropout() function checks whether they are protected or not
     */
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
