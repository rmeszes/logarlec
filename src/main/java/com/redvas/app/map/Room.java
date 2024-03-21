package com.redvas.app.map;

import com.redvas.app.Steppable;
import com.redvas.app.items.Item;
import com.redvas.app.players.Player;

import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Room implements Steppable {



    private int getProfessorCounter() {}
    private boolean isGaseous() {}
    protected static final Logger logger = Logger.getLogger("Item");

    static {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINEST);
        logger.addHandler(handler);
        logger.setLevel(Level.FINEST);
    }

    private void knockoutEveyone() {
        for (Player p : getOccupants())
            p.faint();
    }

    private List<Player> getOccupants() {}
    private void removeOccupant(Player player) {}

    private void addOccupant(Player player) {}

    private void dropoutUndergraduates() {

    }
    private Boolean canAccept() {}

    private Boolean isAccessible(Room targetRoom) {}

    public void setGas() {
        logger.fine("Setting gaseous state for Room object to true");
    }

    public void professorEntered() {
        dropoutUndergraduates();

        logger.fine("Increasing professor counter");
    }

    public void professorLeft() {
        logger.fine("Decreasing professor counter");
    }

    public void paralyzeProfessors() {
        for (Player p : getOccupants())
            p.paralyze();
    }

    public void addItem(Item item) {
        logger.fine("Adding item to item list of Room object");
    }

    public Boolean transfer(Player who, Room to) {
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
        if (isGaseous())
            knockoutEveyone();

        if (getProfessorCounter() > 0)
            dropoutUndergraduates();
    }
}
