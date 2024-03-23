package com.redvas.app.items;

public class RottenCamembert extends Item {
    @Override
    public void use() {
        logger.fine(this + " is being used...");
        owner().where().setGas();
        destroy();
    }

    /**
     * @return Az Item neve
     */
    @Override
    public String toString() {
        return "Cabbage Camembert";
    }
}
