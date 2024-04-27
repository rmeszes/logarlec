package com.redvas.app.items;

import com.redvas.app.map.rooms.Room;
import com.redvas.app.players.Player;

public class Logarlec extends Item {

    protected Logarlec(Integer id, Player owner) {
        super(id, owner);
    }
    public Logarlec(Integer id, Room whichRoom) {
        super(id, whichRoom);
    }

    /** it means winning the game
     *
     * @param who: player that will pick up
     */
    @Override
    public void pickup(Player who) {
        logger.fine(() -> this + " is being picked up by " + who);
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
