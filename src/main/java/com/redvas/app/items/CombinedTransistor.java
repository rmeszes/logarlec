package com.redvas.app.items;

import com.redvas.app.App;
import com.redvas.app.map.Room;
import com.redvas.app.players.Player;

public class CombinedTransistor extends Item{

    private CombinedTransistor pair;
    private boolean isActive;

    public void setPair(CombinedTransistor pair){
        this.pair = pair;
    }

    /** if user says yes, transistors are activated
     * player can teleport
     *
     */
    @Override
    public void use(){
        System.out.print("Has the first transistor been placed?");
        String input = App.reader.nextLine();
        if (input.equals("y")){
            logger.fine("Activated the transistors");
        }
        else{
            logger.fine("You must place the first transistor before activation");
        }
    }

    /**
     *
     * @param who: player that will pick up
     */
    @Override
    public void pickup(Player who) {
        logger.fine("Combined Transistor can't be picked up");
    }

    /** 3 possible outcomes:
     * a) this is the first transistor
     * b) this is the second, but it is not activated yet
     * c) this is the second and it is already activated
     *
     */
    @Override
    public void dispose() {
        System.out.print("Is this the first transistor placement?");
        String input = App.reader.nextLine();
        if (input.equals("y")){
            setDisposedPair(new Transistor());
            logger.fine("Placed first part of Combined Transistor");
        }
        else{
            System.out.print("Has the second transistor been activated?");
            input = App.reader.nextLine();
            if (input.equals("y")){
                owner().moveTo(new Room());
                logger.fine("Transported player to the first transistor's room");
            }
            else{
                logger.fine("Cannot place second half of transistor without activating it first");
            }
        }
    }

    /**
     *
     * @param transistor: the one that will be put to the floor
     */
    private void setDisposedPair(Transistor transistor){
        logger.fine(() -> "Disposed " + transistor.toString() +
                         " in this room: " + transistor.where());
    }

    /**
     * @return Az Item neve
     */
    @Override
    public String toString() {
        return "Combined Transistor";
    }
}
