package com.redvas.app.players;

import com.redvas.app.Game;
import com.redvas.app.items.AirFreshener;
import com.redvas.app.map.rooms.Room;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class Janitor extends Player implements ProximityListener{
    public Janitor(Integer id, Room room, Game game) {
        super(id, room, game);
        where.subscribeToProximity(this);
        where.addOccupant(this);
    }

    private void sendEveryoneOut(List<Player> players) {
        for(Player player : players) {
            while(player.where() == where() && player != this) {
                boolean moved = false;
                for(Room room : where().getAccessibleRooms()) {
                    if(Boolean.TRUE.equals(room.canAccept())) {
                        player.moveTo(room);
                        logger.fine("A player was sent outside");
                        moved = true;
                        break;
                    }
                }
                if(!moved) return; //If all rooms are full, the rest stays
            }
        }
    }

    public void affect(ProximityListener listener) {
        getAffected(this);
    }

    @Override
    public Element savePhantomListenerXML(Document document) {
        return null;
    }

    @Override
    public void pickLogarlec() {
        //can't
    }

    /**
     * Janitors move randomly
     */
    @Override
    public void step() {
        Room room = randomMove();
        if(room != null) {
            room.subscribeToProximity(this);
        }
    }

    @Override
    public void paralyze() {
        //can't
    }

    @Override
    public void dropout() {
        //can't
    }

    @Override
    public boolean useItem(int index) {
        //can't
        return false;
    }

    @Override
    public String toString() {
        return "Janitor";
    }

    /**
     *
     * @param newcomer: player that just entered, needs to be sent out
     */
    @Override
    public void proximityChanged(Player newcomer) {
        List<Player> players = new ArrayList<>();
        players.add(newcomer);
        sendEveryoneOut(players);
    }

    /**
     *
     * @param proximity: still in the room by the end of round
     *
     */
    @Override
    public void proximityEndOfRound(List<Player> proximity) {
        sendEveryoneOut(proximity);
        logger.fine("Janitor has sent you out");
    }

    /**
     *
     * @param proximity: list of players who are still in the room by the beginning of round
     *                 need to be sent out
     */
    @Override
    public void proximityInitially(List<Player> proximity) {
        sendEveryoneOut(proximity);
        logger.fine("Janitor has sent you out");
    }

    @Override
    public int listenerPriority() {
        return 3;
    }

    @Override
    public void getAffected(Janitor by) {
        //nothing
    }

    @Override
    public void getAffected(AirFreshener by) {
        //nothing
    }

    /**
     *
     * @param room: randomly chosen room where they move
     */
    @Override
    public void moveTo(Room room) {
        super.moveTo(room);
        room.subscribeToProximity(this);
        logger.fine("Janitor has moved");
    }
}
