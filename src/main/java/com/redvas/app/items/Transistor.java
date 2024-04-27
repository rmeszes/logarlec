package com.redvas.app.items;

import com.redvas.app.map.Rooms.Room;
import com.redvas.app.players.Player;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Transistor extends Item {
    public Transistor(Integer id, Room whichRoom) {
        super(id, whichRoom);
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
