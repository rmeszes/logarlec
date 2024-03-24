package com.redvas.app.map;

import com.redvas.app.App;
import com.redvas.app.Steppable;
import com.redvas.app.items.Item;
import com.redvas.app.items.RottenCamembert;
import com.redvas.app.players.Player;
import com.redvas.app.players.Undergraduate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Room implements Steppable {
    /**
     *
     * @param item: someone picked it up
     */
    public void removeItem(Item item) {
        logger.fine(()->"Room item inventory no longer holds this " + item);
    }

    /**
     *
     * @param index: type of item
     * @return something, it will probably be random
     */
    public Item getItem(int index) { return new RottenCamembert(); }

    /**
     *
     * @return int: number of profs
     */
    private int getProfessorCounter() { return 0; }

    /** if true, everyone inside faints without a mask
     *
     * @return bool: gas state of the room
     */
    private boolean isGaseous() { return false; }

    protected static final Logger logger = Logger.getLogger("Item");

    static {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINEST);
        logger.addHandler(handler);
        logger.setLevel(Level.FINEST);
    }

    public Room() {
        logger.fine("Room init");
    }

    /** makes everyone faint
     *
     */
    private void knockoutEveyone() {
        logger.fine("Room causes every occupant to faint");

        for (Player p : getOccupants())
            p.faint();
    }

    /**
     *
     * @return list: players inside
     */
    private List<Player> getOccupants() { return new ArrayList<>();    }

    /**
     *
     * @param player: the one that left the room
     */
    private void removeOccupant(Player player) {
        logger.fine(()->"Room occupant list no longer contains this " + player);
    }

    /**
     *
     * @param player: the one that stepped inside
     */
    private void addOccupant(Player player) {
        logger.fine(()->"Room occupant list now contains " + player);
    }

    /** undergrad has lost the game
     *
     */
    private void dropoutUndergraduates() {
        logger.fine("Room is causing every Undergraduate occupant to be dropped out");
        new Undergraduate().dropout();
    }

    /**
     *
     * @return bool: is there space in the room
     */
    private Boolean canAccept() {
        System.out.print("Can this room accept more people? (y/n)");
        String value = App.reader.nextLine();

        if(value.equals("y")) {
            logger.fine("The room has free space");
            return true;
        }
        else{
            logger.fine("The room doesn't have free space");
            return false;
        }
    }

    /**
     *
     * @param targetRoom: where player wants to move
     * @return bool: whether it is neighboring or not
     */
    private Boolean isAccessible(Room targetRoom) { return new Door().isPassable(); }

    /** initialization or someone opened a Camembert
     *
     */
    public void setGas() {
        logger.fine("Room is now gaseous");
    }

    /** if there are undergrads, they lose the game
     *
     */
    public void professorEntered() {
        dropoutUndergraduates();

        logger.fine("Room professor counter increased by one");
    }


    public void professorLeft() {
        logger.fine("Room professor counter decreased by one");
    }

    /** only profs
     *
     */
    public void paralyzeProfessors() {
        logger.fine("Room is causing every Professor occupant to be paralyzed");

        for (Player p : getOccupants())
            p.paralyze();
    }

    /** initialization or someone put it down
     *
     * @param item: that was added to the room
     */
    public void addItem(Item item) {
        logger.fine(()->"Room item inventory was added to a(n) " + item);
    }

    /**
     *
     * @param who: person that exits
     * @param to: room where they move
     * @return bool: whether they managed to move
     */
    public Boolean transfer(Player who, Room to) {
        logger.fine(()->"Room commences the transfer procedure of " + who);

        if (isAccessible(to)) {
            if (to.canOccupy(who)) {
                removeOccupant(who);
                who.moveTo(to);
                return true;
            }
            else return false;
        }
        else return false;
    }

    /**
     *
     * @param who: player who wants to enter
     * @return bool: whether they can
     */
    public Boolean canOccupy(Player who) {
        logger.fine(()->"Room commences the verified adoption of " + who);

        if (canAccept()) {
            addOccupant(who);

            if (getProfessorCounter() > 0)
                who.dropout();

            if (isGaseous())
                who.faint();

            return true;
        }
        else return false;
    }

    // Ez azert van itt, hogy olyan esetekben amikor valakin maszk van, nem ajul el, hanem csak akkro amikor mar bent van es lejar majd a protekcio
    // A dropout eseten hasonlo az erveles, mert a TVSZ/HolyBeer lejaratat is figyelembe kell venni

    /** for special cases, checking the protection of undergrads
     *
     */
    @Override
    public void step() {
        logger.fine("Room is on its turn");

        if (isGaseous())
            knockoutEveyone();

        if (getProfessorCounter() > 0)
            dropoutUndergraduates();
    }

    public void mergeWithRoom(Room other) {
        logger.fine("Setting this room's attributes to new ones..");
        other.destroy();
        //labyrinth.update()
    }

    /**
     * After the room is merge it has to disappear
     */
    public void destroy() {
        logger.fine("This room got destroyed");
    }
}
