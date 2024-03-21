package com.redvas.app.items;

public class TVSZ extends Item {
    @Override
    public void use() {
        owner().setProtectionFor(1);
        // TODO: megsemmisul
    }
}
