package com.redvas.app.players;

import com.redvas.app.Game;
import com.redvas.app.map.Room;

import java.util.List;

public class Janitor extends Player{
    public Janitor(Room room, Game game) {
        super(room, game);
    }

    private void sendEveryoneOut(List<Player> players) {

    }

    public void affect() {}

    @Override
    public void pickLogarlec() {

    }

    @Override
    public void step() {
        
    }

    @Override
    public void paralyze() {

    }

    @Override
    public void dropout() {

    }

    @Override
    public boolean useItem(int index) {
        return true;
    }

    @Override
    public String toString() {
        return null;
    }
}
