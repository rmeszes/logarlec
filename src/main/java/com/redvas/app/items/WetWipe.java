package com.redvas.app.items;

public class WetWipe extends Item {
    @Override
    public void use() {
        owner().where().paralyzeProfessors();
        destroy();
    }
}
