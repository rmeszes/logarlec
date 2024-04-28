package com.redvas.app.items;

import com.redvas.app.map.Rooms.Room;
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

    public void use() {

    }

    @Override
    public String toString() {
        return "AirFreshener";
    }

    public void proximityChanged(Player newcomer) {}
    public void proximityEndOfRound(List<Player> proximity) {}
    public void proximityInitially(List<Player> proximity) {}
    public int listenerPriority() {
        return 1;
    }
    public void getAffected(Janitor by) {}
    public void getAffected(AirFreshener by) {}

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
