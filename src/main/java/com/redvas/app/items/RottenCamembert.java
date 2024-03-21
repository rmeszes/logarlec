package com.redvas.app.items;

public class RottenCamembert extends Item {
    @Override
    public void use() {
        owner().where().setGas();
        // TODO: megsemmisul
    }
}
