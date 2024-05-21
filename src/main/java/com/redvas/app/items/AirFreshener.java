package com.redvas.app.items;

import com.redvas.app.map.rooms.Room;
import com.redvas.app.players.Janitor;
import com.redvas.app.players.Player;
import com.redvas.app.players.ProximityListener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

public class AirFreshener extends Item implements ProximityListener {

    protected AirFreshener(Integer id, Player owner) {
        super(id, owner);
    }
    public AirFreshener(Integer id, Room whichRoom, Boolean isListener) {
        super(id, whichRoom, isListener);

        if (Boolean.TRUE.equals(isListener))
            whichRoom.subscribeToProximity(this);
    }
    public AirFreshener(Integer id, Room whichRoom) {
        super(id, whichRoom, false);
    }

    /**
     * using it means the item subscribes to the proximity of the room
     * this proximity will change the gaseous state, it will stop being gaseous
     */
    @Override
    public void use() {
        logger.finest("Airfreshener used");
        if(isReal)
            owner.where().subscribeToProximity(this);
        else
            logger.fine(()-> this + " was a fake item.");
        destroy();
    }

    @Override
    public String toString() {
        return "AirFreshener";
    }

    /**
     *
     * @param newcomer: the one who just entered the room in this round
     */
    public void proximityChanged(Player newcomer) {
        logger.finest("proximityChanged");
    }

    /**
     *
     * @param proximity: list of players who are still in the room by the end of the round
     *                 because they had a chance to leave, or put on a mask
     */
    public void proximityEndOfRound(List<Player> proximity) {
        logger.finest("proximityEndOfRound");
    }

    /**
     *
     * @param proximity: list of players who are in the room by the beginning of the round
     */
    public void proximityInitially(List<Player> proximity) {
        logger.finest("proximityInitially");
    }

    /**
     *
     * @return 4: we make sure that if two objects are being used at the same time
     * eg: someone opens a RottenCamembert but someone also used an AirFreshener
     * then there is a rule (priority rule) which allows us to decide what will affect the room
     */
    public int listenerPriority() {
        return 4;
    }

    /**
     *
     * @param by: this function does not do anything, Janitor can not affect the AirFreshener
     */
    public void getAffected(Janitor by) {
        logger.finest("getAffected");
    }
    public void getAffected(AirFreshener by) {
        logger.finest("getAffected");
    }

    @Override
    public void affect(ProximityListener listener) {
        listener.getAffected(this);
    }

    @Override
    public Element savePhantomListenerXML(Document document) {
        Element listener = document.createElement("phantom_listener");
        listener.setAttribute("type", getClass().getName());
        return listener;
    }
}
