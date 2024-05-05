package com.redvas.app.items;

import com.redvas.app.map.rooms.Room;
import com.redvas.app.players.Player;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Map;

public class CombinedTransistor extends Item {

    private CombinedTransistor pairedWith;
    private boolean isActive = false;

    protected CombinedTransistor(Integer id, Player owner) {
        super(id, owner);
    }
    public CombinedTransistor(Integer id, Room whichRoom) {
        super(id, whichRoom, false);
    }

    private CombinedTransistor(Integer id, Room whichRoom, Boolean isListener) {
        super(id, whichRoom, false);
    }


    public void setPair(CombinedTransistor pair){
        pairedWith = pair;
    }

    /** if user says yes, transistors are activated
     * player can teleport
     *
     */
    @Override
    public void use(){
        if (pairedWith.isInRoom){ // if the pair is on the ground already (in half state)
            isActive = true;
            logger.fine(() -> "Activated the transistors");
        }
        else {
            logger.fine(() -> "You must place the first transistor before activation");
        }
    }


    @Override
    public void loadXML(Element ct, Map<Integer, Item> id2item) {
        super.loadXML(ct, id2item);
        pairedWith = (CombinedTransistor) id2item.get(Integer.parseInt(ct.getAttribute("paired_with")));
        isActive = Boolean.parseBoolean(ct.getAttribute("is_active"));
    }

    @Override
    public Element saveXML(Document document) {
        Element transistor = super.saveXML(document);
        transistor.setAttribute("paired_with", String.valueOf(pairedWith.getID()));
        transistor.setAttribute("is_active", String.valueOf(isActive));
        return transistor;
    }

    /**
     *
     * @param who: player that will pick up
     */
    @Override
    public void pickup(Player who) {
        if (!pairedWith.isActive && isInRoom){ // if this is on the ground and not active (in half state)
            super.pickup(who);
            logger.fine(() -> "Picked up CombinedTransistor");
        }
        else {
            logger.fine(() -> "You cannot pick up a CombinedTransistor right now!");
        }
    }

    /** 3 possible outcomes:
     * a) this is the first transistor
     * b) this is the second, but it is not activated yet
     * c) this is the second and it is already activated
     *
     */
    @Override
    public void dispose() {
        if (!pairedWith.isInRoom) { // if pair is not on the ground (in base state)
            super.dispose();
            logger.fine(() -> "Placed first CombinedTransistor");
        }
        else {
            if (isActive) { // if pair is on the ground and this is activated (in active state)
                super.dispose();
                owner.moveTo(pairedWith.whichRoom);
                isActive = false;
                pairedWith.pickup(owner);
                logger.fine(() -> "Transported player to the first CombinedTransistor's room");
                // graphics repaint() needed
            }
            else { // if pair is on the ground and this is not activated (in half state)
                logger.fine(() -> "Cannot place second CombinedTransistor without activating it first");
            }
        }
    }

    /**
     * @return Az Item neve
     */
    @Override
    public String toString() {
        return "Combined Transistor, owner: " + getOwner().toString() + ", id: " + getID();
    }
}
