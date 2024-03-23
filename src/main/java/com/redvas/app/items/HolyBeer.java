package com.redvas.app.items;

public class HolyBeer extends Item {
    @Override
    public void use() {
        logger.fine(this + " is being used...");
        owner().setProtectionFor(3);
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