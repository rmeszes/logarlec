package com.redvas.app.items;

import com.redvas.app.map.Room;

public class FFP2 extends Item {

    public FFP2(Room whichRoom) {
        super(whichRoom);
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
