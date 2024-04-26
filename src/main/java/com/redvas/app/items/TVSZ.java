package com.redvas.app.items;

import com.redvas.app.map.Rooms.Room;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.print.Doc;

public class TVSZ extends Item {
    public TVSZ(Room whichRoom) {
        super(whichRoom);
    }

    @Override
    public Element saveXML(Document document) {
        Element tvsz = super.saveXML(document);
        tvsz.setAttribute("uses", String.valueOf(uses));
        return tvsz;
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
