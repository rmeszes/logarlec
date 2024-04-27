package com.redvas.app.items;

import com.redvas.app.map.Rooms.Room;
import com.redvas.app.players.Player;

public class HolyBeer extends Item {
    public HolyBeer(Integer id, Player owner) {
        super(id, owner);
    }

    public HolyBeer(Integer id, Room whichRoom) {
        super(id, whichRoom);
    }

    /** activate the item (protection from prof)
     *
     */
    @Override
    public void use() {
        logger.finest(() -> this + " is being used...");
        getOwner().setProtectionFor(3);
        destroy();
    }

    /**
     * @return Az Item neve
     */
    @Override
    public String toString() {
        return "Holy Beer";
    }
}
