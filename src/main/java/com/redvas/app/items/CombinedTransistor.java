package com.redvas.app.items;

import com.redvas.app.map.Room;
import com.redvas.app.players.Player;

public class CombinedTransistor extends Item {

    private CombinedTransistor pairedWith;
    private boolean isActive = false;

    protected CombinedTransistor(Room whichRoom) {
        super(whichRoom);
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
        if (pairedWith.whichRoom != null){ // if the pair is on the ground already (in half state)
            isActive = true;
            logger.fine(() -> "Activated the transistors");
        }
        else {
            logger.fine(() -> "You must place the first transistor before activation");
        }
    }

    /**
     *
     * @param who: player that will pick up
     */
    @Override
    public void pickup(Player who) {
        if (pairedWith.whichRoom != null){ // if the pair is on the ground already (in half state)
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
        if (pairedWith.whichRoom == null) { // if pair is not on the ground (in base state)
            super.dispose();
            logger.fine(() -> "Placed first CombinedTransistor");
        }
        else {
            if (isActive) { // if pair is on the ground and this is activated (in active state)
                CombinedTransistor tmp = new CombinedTransistor(whichRoom);
                tmp.owner = getOwner();
                super.dispose();
                tmp.owner.moveTo(pairedWith.whichRoom);
                isActive = false;
                pairedWith.pickup(tmp.owner);
                logger.fine(() -> "Transported player to the first CombinedTransistor's room: " + pairedWith.whichRoom.toString());
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
        return "Combined Transistor, owner: " + getOwner().toString() + ", room: " + getRoom().toString();
    }
}
