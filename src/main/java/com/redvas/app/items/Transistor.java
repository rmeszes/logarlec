package com.redvas.app.items;

public class Transistor extends Item {
    @Override
    public void merge(Transistor item){
        Item tmp = new CombinedTransistor();
        tmp.setOwner(owner());
        owner().addToInventory(tmp);
        destroy();
        item.destroy();
        logger.fine("Merged transistor");
    }


    /**
     * @return Az Item neve
     */
    @Override
    public String toString() {
        return "Transistor";
    }
}
