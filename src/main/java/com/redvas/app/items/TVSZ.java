package com.redvas.app.items;

import com.redvas.app.map.rooms.Room;
import com.redvas.app.players.Player;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TVSZ extends Item {
    public TVSZ(Integer id, Room whichRoom) {
        super(id, whichRoom, false);
    }

    private TVSZ(Integer id, Room whichRoom, Boolean isListener) {
        super(id, whichRoom, false);
    }

    @Override
    public Element saveXML(Document document) {
        Element tvsz = super.saveXML(document);
        tvsz.setAttribute("uses", String.valueOf(uses));
        return tvsz;
    }
    protected TVSZ(Integer id, Player owner) {
        super(id, owner);
    }

    /**
     *
     * @return int: how many is left
     */
    private int uses = 3;

    /** until it can be used (3 times)
     * provides protection
     */
    @Override
    public void use() {
        logger.fine(() -> this + " is being used...");
        getOwner().setProtectionFor(1);

        if (uses == 0)
            destroy();
        else uses--;
    }

    /**
     * @return Az Item neve
     */
    @Override
    public String toString() {
        return "TVSZ";
    }
}
