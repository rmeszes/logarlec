package com.redvas.app.items;

import com.redvas.app.Game;
import com.redvas.app.players.Player;

public class Logarlec extends Item {
    @Override
    public void pickup(Player who) {
        Game.undergraduateVictory();
    }

    /**
     * @return Az Item neve
     */
    @Override
    public String toString() {
        return "logarléc";
    }


}
