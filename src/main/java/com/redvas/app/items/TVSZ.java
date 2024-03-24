package com.redvas.app.items;

public class TVSZ extends Item {
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
        owner().setProtectionFor(1);
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
