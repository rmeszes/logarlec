package com.redvas.app.items;

public class RottenCamembert extends Item {
    @Override
    public void use() {
        owner().where().setGas();
        destroy();
    }

    /**
     * @return Az Item neve
     */
    @Override
    public String toString() {
        return "káposztás Camembert";
    }
}
