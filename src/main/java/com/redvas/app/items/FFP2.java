package com.redvas.app.items;

public class FFP2 extends Item {
    /** protection from gas is activated for a given number of rounds
     *
     */
    @Override
    public void use() {
        logger.fine(this + " is being used...");
        owner().useFFP2();
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
