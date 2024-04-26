package com.redvas.app.players;

import com.redvas.app.Game;
import com.redvas.app.items.AirFreshener;
import com.redvas.app.map.Rooms.Room;

import java.util.ArrayList;
import java.util.List;

public class Janitor extends Player implements ProximityListener {
    public Janitor(Room room, Game game) {
        super(room, game);
    }

    private void sendEveryoneOut(List<Player> players) {
        for(Player player : players) {
            while(player.where() == where()) {
                boolean moved = false;
                for(Room room : where().getAccessibleRooms()) {
                    if(room.canAccept()) {
                        player.moveTo(room);
                        moved = true;
                    }
                }
                if(!moved) return;//If all rooms are full, the rest stays
            }
        }
    }

    public void affect(ProximityListener listener) {
        getAffected(this);
    }

    @Override
    public void pickLogarlec() {
        //can't
    }

    @Override
    public void step() {
        List<Room> rooms = where().getAccessibleRooms();
        for(Room room : rooms) {
            if(room.canAccept()) {
                moveTo(room);
                break;
            }
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
        return String.format("Janitor room: %s", where().toString());
    }

    @Override
    public void proximityChanged(Player newcomer) {
        List<Player> players = new ArrayList<>();
        players.add(newcomer);
        sendEveryoneOut(players);
    }

    @Override
    public void proximityEndOfRound(List<Player> proximity) {
        sendEveryoneOut(proximity);
    }

    @Override
    public void proximityInitially(List<Player> proximity) {
        sendEveryoneOut(proximity);
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

    @Override
    public void moveTo(Room room) {
        super.moveTo(room);
        room.subscribeToProximity(this);

    }
}
