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
        CombinedTransistor ct1 = new CombinedTransistor();
        CombinedTransistor ct2 = new CombinedTransistor();
        ct1.setPair(ct2);
        ct2.setPair(ct1);
        ct1.owner = getOwner();
        ct2.owner = getOwner();
        ct1.whichRoom = null;
        ct2.whichRoom = null;
        getOwner().addToInventory(ct1);
        getOwner().addToInventory(ct2);
        destroy();
        item.destroy();
        logger.fine(() -> "Merged transistor");
    }


    /**
     * @return Az Item neve
     */
    @Override
    public String toString() {
        return "Transistor";
    }
}
