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

        if (isListener)
            whichRoom.subscribeToProximity(this);
    }
    public AirFreshener(Integer id, Room whichRoom) {
        super(id, whichRoom, false);
    }

    @Override
    public void use() {
        logger.finest("Airfreshener used");
        owner.where().subscribeToProximity(this);
        destroy();
    }

    @Override
    public String toString() {
        return "AirFreshener";
    }

    public void proximityChanged(Player newcomer) {
        logger.finest("proximityChanged");
    }
    public void proximityEndOfRound(List<Player> proximity) {
        logger.finest("proximityEndOfRound");
    }
    public void proximityInitially(List<Player> proximity) {
        logger.finest("proximityInitially");
    }
    public int listenerPriority() {
        return 4;
    }
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
