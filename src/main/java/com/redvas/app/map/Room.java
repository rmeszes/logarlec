package com.redvas.app.map;

import com.redvas.app.Steppable;
import com.redvas.app.items.Item;
import com.redvas.app.items.RottenCamembert;
import com.redvas.app.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Room implements Steppable {
    public void removeItem(Item item) {
        logger.fine("Room item inventory was confiscated of a(n) " + item);
    }
    public Item getItem(int index) { return new RottenCamembert(); }

    private int getProfessorCounter() { return 0; }
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

    private void knockoutEveyone() {
        logger.fine("Room causes every occupant to faint");

        for (Player p : getOccupants())
            p.faint();
    }

    private List<Player> getOccupants() { return new ArrayList<>();    }
    private void removeOccupant(Player player) {
        logger.fine("Room occupant list was confiscated of a(n) " + player);
    }
    private void addOccupant(Player player) {
        logger.fine("Room occupant list was added to a(n) " + player);
    }
    private void dropoutUndergraduates() {
        logger.fine("Room is causing every Undergraduate occupant to be dropped out");
    }
    private Boolean canAccept() { return true; }

    private Boolean isAccessible(Room targetRoom) { return true; }

    public void setGas() {
        logger.fine("Room is now gaseous");
    }

    public void professorEntered() {
        dropoutUndergraduates();

        logger.fine("Room professor counter increased by one");
    }

    public void professorLeft() {
        logger.fine("Room professor counter decreased by one");
    }

    public void paralyzeProfessors() {
        logger.fine("Room is causing every Professor occupant to be paralyzed");

        for (Player p : getOccupants())
            p.paralyze();
    }

    public void addItem(Item item) {
        logger.fine("Room item inventory was added to a(n) " + item);
    }

    public Boolean transfer(Player who, Room to) {
        logger.fine("Room commences the transfer procedure of " + who);

        if (isAccessible(to)) {
            if (to.canOccupy(who)) {
                removeOccupant(who);
                return true;
            }
            else return false;
        }
        else return false;
    }

    public Boolean canOccupy(Player who) {
        logger.fine("Room commences the verified adoption of " + who);

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
    @Override
    public void step() {
        logger.fine("Room is on its turn");

        if (isGaseous())
            knockoutEveyone();

        if (getProfessorCounter() > 0)
            dropoutUndergraduates();
    }
}
