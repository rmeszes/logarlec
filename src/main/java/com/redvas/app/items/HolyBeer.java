package com.redvas.app.items;

import com.redvas.app.map.rooms.Room;
import com.redvas.app.players.Player;

public class HolyBeer extends Item {
    public HolyBeer(Integer id, Player owner) {
        super(id, owner);
    }

    public HolyBeer(Integer id, Room whichRoom) {
        super(id, whichRoom, false);
    }

    private HolyBeer(Integer id, Room whichRoom, Boolean isListener) {
        super(id, whichRoom, false);
    }

    /** activate the item (protection from prof)
     *
     */
    @Override
    public void use() {
        logger.finest(() -> this + " is being used...");
        if(isReal)
            getOwner().setProtectionFor(3);
        else
            logger.fine(() -> this + " was a fake item.");
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
