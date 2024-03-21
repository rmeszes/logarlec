package com.redvas.app.items;

public class HolyBeer extends Item {
    @Override
    public void use() {
        owner().setProtectionFor(3);
        // TODO: megsemmisul
    }
}
