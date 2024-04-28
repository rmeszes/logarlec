package com.redvas.app.items;

import com.redvas.app.map.Rooms.Room;
import com.redvas.app.players.Player;

public class FFP2 extends Item {
    public FFP2(Integer id, Player owner) {
        super(id, owner);
    }
    public FFP2(Integer id, Room whichRoom) {
        super(id, whichRoom, false);
    }

    private FFP2(Integer id, Room whichRoom, Boolean isListener) {
        super(id, whichRoom, false);
    }

    /** protection from gas is activated for a given number of rounds
     *
     */
    @Override
    public void use() {
        logger.fine(() -> this + " is being used...");
        getOwner().useFFP2();
        destroy();
    }

    /**
     * @return Az Item neve
     */
    @Override
    public String toString() {
        return "FFP2 mask";
    }
}
