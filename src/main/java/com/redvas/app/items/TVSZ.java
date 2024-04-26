package com.redvas.app.items;

import com.redvas.app.map.Room;

public class TVSZ extends Item {
    protected TVSZ(Room whichRoom) {
        super(whichRoom);
    }

    /**
     *
     * @return int: how many is left
     */
    private int getUses() { return 3; }
    private void used() { }

    /** until it can be used (3 times)
     * provides protection
     */
    @Override
    public void use() {
        logger.fine(() -> this + " is being used...");
        getOwner().setProtectionFor(1);
        used();

        if (getUses() == 0)
            destroy();
    }

    /**
     * @return Az Item neve
     */
    @Override
    public String toString() {
        return "TVSZ";
    }
}
