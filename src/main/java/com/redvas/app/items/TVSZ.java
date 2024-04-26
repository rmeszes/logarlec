package com.redvas.app.items;

import com.redvas.app.map.Rooms.Room;

public class TVSZ extends Item {
    public TVSZ(Room whichRoom) {
        super(whichRoom);
    }

    /**
     *
     * @return int: how many is left
     */
    private int uses = 3;

    /** until it can be used (3 times)
     * provides protection
     */
    @Override
    public void use() {
        logger.fine(() -> this + " is being used...");
        getOwner().setProtectionFor(1);

        if (uses == 0)
            destroy();
        else uses--;
    }

    /**
     * @return Az Item neve
     */
    @Override
    public String toString() {
        return "TVSZ";
    }
}
