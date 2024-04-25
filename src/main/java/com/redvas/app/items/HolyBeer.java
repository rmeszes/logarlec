package com.redvas.app.items;

public class HolyBeer extends Item {
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
