package com.redvas.app.players;

import com.redvas.app.App;
import com.redvas.app.Game;
import com.redvas.app.Steppable;
import com.redvas.app.items.Transistor;
import com.redvas.app.map.rooms.Room;
import com.redvas.app.ui.GameOverListener;
import com.redvas.app.ui.players.listeners.UndergraduateChangeListener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class Undergraduate extends Player implements Steppable {
    private int protection;
    private boolean dropScheduled;
    private boolean isActive;
    private boolean hasLogarlec;

    public Undergraduate(Integer id, Room room, Game game) {
        super(id, room, game);
        this.protection = 0;
        this.dropScheduled = false;
        this.hasLogarlec = false;
        logger.fine(() -> this + " created");
        game.addUndergraduate();
        // where.addOccupant(this);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean b) {
        if(isActive != b) {
            isActive = b;
            listener.activeStateChanged();
        }
    }

    @Override
    public Element saveXML(Document document) {
        Element undergraduate = super.saveXML(document);
        undergraduate.setAttribute("protection", String.valueOf(protection));
        return undergraduate;
    }

    /**
     *
     * @return int: how long are they protected from profs
     */
    private int getProtectedRounds() { return protection; }

    /**
     *
     * @param rounds: how long are they protected from profs
     */
    private void setProtectedRounds(int rounds) {
        if(rounds > protection) protection = rounds;
    }

    /**
     *
     * @param rounds: number of rounds until they are protected
     */
    @Override
    public void setProtectionFor(int rounds) {
        setProtectedRounds(rounds);
    }

    /** calls the win function
     *
     */
    @Override
    public void pickLogarlec() {
        getGame().undergraduateVictory();
        hasLogarlec = true;
    }

    protected static final Logger logger = App.getConsoleLogger(Undergraduate.class.getName());

    /** prints how long the player is still fainted if they are
     * otherwise they can make a step in the game
     *
     */
    @Override
    public void step() {
        setActive(true);
        if (faintCountdown == 0)
            getCommand();

        super.step();
        setActive(false);
    }

    private UndergraduateChangeListener listener = null;

    public void setListener(UndergraduateChangeListener listener) {
        this.listener = listener;
    }

    /**
     * this is the implementation of the steps only the undergraduates can make
     * like move, act, list
     */
    private void getCommand() {
        // if (ffp2Countdown > 0) ffp2Countdown--;
        HashMap<String, Supplier<Boolean>> cmds = new HashMap<>();
        cmds.put("move", this::consoleMove);
        cmds.put("act", this::consoleAct);
        cmds.put("list", this::consoleList);
        HashMap<String, String> man = new HashMap<>();
        man.put("list", "list");
        man.put("move", "move");
        man.put("act", "act");
        man.put("pass", "pass");
        Scanner scanner = App.reader;

        while (!hasLogarlec) {
            StringBuilder builder = new StringBuilder();
            builder.append("Choose command:\n");

            for (Map.Entry<String, String> kvp : man.entrySet())
                builder.append(String.format("%s%n", kvp.getKey()));

            logger.fine(builder::toString);
            String cmd = scanner.nextLine();
            Supplier<Boolean> selection = null;

            if ((selection = cmds.getOrDefault(cmd, null)) != null) {
                if (Boolean.TRUE.equals(selection.get())) {
                    man.remove(cmd);
                    cmds.remove(cmd);
                }
            }
            else if (Objects.equals(cmd, "pass")) {
                return;
            }
            else
                logger.fine("Command not recognized");
        }
    }

    /** only profs can be
     *
     */
    @Override
    public void paralyze() {
        // Nem csinal semmit
    }

    /** losing the game
     *
     */
    @Override
    public void dropout() {
        if (getProtectedRounds() > 0)
            logger.fine("Undergraduate was protected from being dropped out");
        else {
            logger.fine(()->"Player " + getID() + " has dropped out");
            game.unRegisterSteppable(this);
            getGame().undergraduateDroppedout();
            if (listener != null)
                listener.droppedOut();
        }
    }

    /**
     *
     * @param index: picked items index
     */
    @Override
    public boolean useItem(int index) {
        logger.fine(() -> this + " chose to use a(n) " + getItem(index));
        getItem(index).use();
        return true;
    }

    /** two possible outcomes: 2 transistors merged
     * or nothing happens because player did not have 2 transistors
     *
     */
    @Override
    public void mergeItems(int i1, int i2) {
        if ((i1 >= 1 && i1 <= 5) && (i2 >= 1 && i2 <= 5)) {
            getItem(i1).merge((Transistor)getItem(i2));
        }
    }

    /**
     * ffp2 grants protection from gas 3 times
     */
    @Override
    public void useFFP2() {
        ffp2Countdown = 3;
    }

    /**
     * if they can not protect themselves somehow
     * they will drop out by the end of the round
     */
    @Override
    public void scheduleDrop() { dropScheduled = true; }

    // getter
    public String getName() { return "Player " + (getID()+1); }
    public int getProtection() { return protection; }
    public boolean getDropScheduled() { return dropScheduled; }

    /**
     *
     * @return string representation of this Player
     */
    @Override
    public String toString() {
        return getName();
    }
}
