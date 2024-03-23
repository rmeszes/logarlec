package com.redvas.app.items;

import com.redvas.app.Game;
import com.redvas.app.players.Player;

public class Logarlec extends Item {
    @Override
    public void pickup(Player who) {
        logger.fine(this + " is being picked up by " + who);
        who.pickLogarlec();
    }

    /**
     * @return Az Item neve
     */
    @Override
    public String toString() {
        return "Logarléc";
    }
}
