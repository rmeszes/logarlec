package com.redvas.app.items;

public class Transistor extends Item {
    /** in case of two unmerged transistors
     * they can be "connected" creating a CTransistor item
     * they stop existing
     *
     * @param item: the one that will be merged
     */
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
