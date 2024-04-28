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

    @Override
    public void use() {
        //TODO kitalálni az egészet, honnan tudjuk van-e másik transistor?
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
        CombinedTransistor ct1 = new CombinedTransistor(-1, whichRoom);
        CombinedTransistor ct2 = new CombinedTransistor(-2, whichRoom);
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
