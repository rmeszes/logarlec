package com.redvas.app.items;

import com.redvas.app.map.rooms.Room;
import com.redvas.app.players.Janitor;
import com.redvas.app.players.Player;
import com.redvas.app.players.ProximityListener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

public class RottenCamembert extends Item implements ProximityListener {
    public RottenCamembert(Integer id, Room whichRoom) {
        super(id, whichRoom, false);
    }

    public RottenCamembert(Integer id, Room whichRoom, Boolean isListener) {
        super(id, whichRoom, isListener);
        whichRoom.subscribeToProximity(this);
    }

    protected RottenCamembert(Integer id, Player owner) {
        super(id, owner);
    }

    /**
     * using it means the item subscribes to the proximity of the room
     * this proximity will change the gaseous state, it will become gaseous
     * and starts making the unprotected players faint
     */
    @Override
    public void use() {
        logger.finest(() -> this + " is being used...");
        if(isReal) {
            owner.where().subscribeToProximity(this);
            whichRoom = owner.where();
        } else
        {
            logger.fine(()-> this + " was a fake item.");
        }
        destroy();
    }

    /**
     * @return Az Item neve
     */
    @Override
    public String toString() {
        return "Cabbage Camembert";
    }

    /**
     *
     * @param newcomer: the one who just entered the room in this round
     */
    @Override
    public void proximityChanged(Player newcomer) {
        newcomer.faint();
        logger.finest(() -> this + " proximity changed");
    }
    /**
     *
     * @param proximity: list of players who are still in the room by the end of the round
     *                 because they had a chance to leave, or put on a mask
     */
    @Override
    public void proximityEndOfRound(List<Player> proximity) {
        knockoutEveryone(proximity);
        logger.finest(() -> this + " proximity endOfRound");
    }
    /**
     *
     * @param proximity: list of players who are in the room by the beginning of the round
     */
    @Override
    public void proximityInitially(List<Player> proximity) {
        knockoutEveryone(proximity);
        logger.finest(() -> this + " proximityInitially");
    }
    /**
     *
     * @return 0: we make sure that if two objects are being used at the same time
     * eg: someone opens a RottenCamembert but someone also used an AirFreshener
     * then there is a rule (priority rule) which allows us to decide what will affect the room
     */
    @Override
    public int listenerPriority() {
        return 0;
    }

    /**
     *
     * @param by: the janitor who just cleaned the room
     *         they can change the gaseous state of the room
     *          it means unsubscribing from the proximity of the room
     */
    @Override
    public void getAffected(Janitor by) {
        whichRoom.unsubscribeFromProximity(this);
    }
    /**
     *
     * @param by: the airfreshener item that was just used
     *         they can change the gaseous state of the room
     *          it means unsubscribing from the proximity of the room
     */
    @Override
    public void getAffected(AirFreshener by) {
        whichRoom.unsubscribeFromProximity(this);
    }

    @Override
    public void affect(ProximityListener listener) {
        //does nothing
    }

    @Override
    public Element savePhantomListenerXML(Document document) {
        Element listener = document.createElement("phantom_listener");
        listener.setAttribute("type", getClass().getName());
        return listener;
    }

    /** makes the players in the proximity faint
     *
     * @param proximity: list of players that have to be affected by the gas
     */
    private void knockoutEveryone(List<Player> proximity) {
        for(Player player: proximity){
            player.faint();
        }
    }
}
