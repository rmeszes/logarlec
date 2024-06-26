package com.redvas.app.items;

import com.redvas.app.map.rooms.Room;
import com.redvas.app.players.Player;

public class Transistor extends Item {
    public Transistor(Integer id, Room whichRoom) {
        super(id, whichRoom, false);
    }

    private Transistor(Integer id, Room whichRoom, Boolean isListener) {
        super(id, whichRoom, false);
    }

    /** using the transistor means trying to merge it
     *  we iterate through the inventory, if we find another Transistor item we merge
     *  else we don't
     *  to avoid type-checks, we just put it in a try catch block
     */
    @Override
    public void use() {
        if(!isReal) {
            logger.fine(this + " was a fake item.");
            destroy();
            return;
        }

        assert owner.getItems() != null : "use() called with no owner";
        for (Item item : owner.getItems()) {
            if(item != this) {
                try {
                    item.merge((Transistor) item);
                    logger.fine("Merged");
                    break;
                } catch (ClassCastException e) {
                    logger.fine("tried merging to another non-transistor");
                }
            }
        }
    }
    protected Transistor(Integer id, Player owner) {
        super(id, owner);
    }

    /** in case of two unmerged transistors
     * they can be "connected" creating a CTransistor item
     * they stop existing
     *
     * @param item: the one that will be merged
     */

    @Override
    public void merge(Transistor item){
        if (item.getClass() != Transistor.class) {
            logger.fine("tried merging to another non-transistor");
            return;
        }
        item.destroy();
        destroy();
        CombinedTransistor ct1 = new CombinedTransistor(-1, this.owner);
        CombinedTransistor ct2 = new CombinedTransistor(-2, this.owner);
        ct1.setPair(ct2);
        ct2.setPair(ct1);
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
