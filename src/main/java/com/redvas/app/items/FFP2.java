package com.redvas.app.items;

public class FFP2 extends Item {
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
