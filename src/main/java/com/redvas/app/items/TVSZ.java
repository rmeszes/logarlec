package com.redvas.app.items;

public class TVSZ extends Item {
    private int getUses() { return 3; }
    private void used() { }

    @Override
    public void use() {
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
