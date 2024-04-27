package com.redvas.app.players;

import com.redvas.app.App;
import com.redvas.app.Game;
import com.redvas.app.Steppable;
import com.redvas.app.items.Item;
import com.redvas.app.map.Direction;
import com.redvas.app.map.Rooms.Room;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//absztrakt class, majd az implementációk lesznek tesztelve
public abstract class Player implements Steppable {
    private int id;
    public int getID() {
        return id;
    }

    public Element saveXML(Document document) {
        Element player = document.createElement("player");
        player.setAttribute("id", String.valueOf(getID()));
        player.setAttribute("faint_countdown", String.valueOf(faintCountdown));
        player.setAttribute("ffp2_countdown", String.valueOf(ffp2Countdown));
        player.setAttribute("where", String.valueOf(where.getID()));
        player.setAttribute("type", this.getClass().getName());
        Element inventory = document.createElement("items");

        for (Item i : items) {
            Element item = i.saveXML(document);
            inventory.appendChild(item);
        }

        player.appendChild(inventory);
        return player;
    }

    //constants
    private final String commandNotRecognizedMsg = "Command not recognised.";

    // tagváltozók
    private Room where;
    private final ArrayList<Item> items;
    private int faintCountdown;     // unsigned int?
    private int ffp2Countdown  = 0;
    protected final Game game;        // akár ez is lehet final

    protected static final Logger logger = App.getConsoleLogger(Player.class.getName());

    // konstruktor
    protected Player(Integer id, Room room, Game game) {
        this.id = id;
        this.where = room;
        this.items = new ArrayList<>();
        this.faintCountdown = 0;
        this.game = game;
        moveTo(room);
        game.registerSteppable(this);
    }
    public void loadXML(Element player) {
        faintCountdown = Integer.parseInt(player.getAttribute("faint_countdown"));
        ffp2Countdown = Integer.parseInt(player.getAttribute("ffp2_countdown"));
    }
    /**
     *
     * @param rounds: number of rounds until they are protected
     */
    public void setProtectionFor(int rounds) {}

    /**
     *
     * @param index: chosen item that they want to pick
     * @return: item that they picked
     */
    protected Item getItem(int index) {     // inventory 1-5ig
        if (index < 1 || index > 5) { throw new IllegalArgumentException();}
        return items.get(index - 1);
    }

    /** this item behaves differently than others
     *
     */
    public abstract void pickLogarlec();

    /** drops the items from inventory
     *
     */
    public void faint() {
        if (ffp2Countdown > 0) {
            faintCountdown = 3;
            dropItems();
        }
    }

    /** currently moving player
     *
     */
    public void step() {    // Ez absztrakt a modell szerint, de akkor amúgy mégsem?
        if (faintCountdown > 0)
            faintCountdown--;
        else {
            if (ffp2Countdown > 0) ffp2Countdown--;
            HashMap<Character, Supplier<Boolean>> cmds = new HashMap<>();
            cmds.put('m', this::consoleMove);
            cmds.put('a', this::consoleAct);
            HashMap<Character, String> man = new HashMap<>();
            man.put('m', "move");
            man.put('a', "act");
            man.put('p', "pass");
            Scanner scnr = new Scanner(System.in);

            while (true) {
                logger.fine("Choose command:");

                for (Map.Entry<Character, String> kvp : man.entrySet())
                    logger.fine(() -> String.format("Cmd: %c (%s)%n\n", kvp.getKey(), kvp.getValue()));

                Character cmd = scnr.nextLine().charAt(0);
                Supplier<Boolean> selection = null;

                if ((selection = cmds.getOrDefault(cmd, null)) != null)
                    if (selection.get())
                    {
                        man.remove(cmd);
                        cmds.remove(cmd);
                    }
                else if (cmd == 'p')
                    return;
                else
                    logger.fine(commandNotRecognizedMsg);
            }
        }
    }

    /**
     *
     * @param item: picked item that they will dispose of
     */
    public void removeFromInventory(Item item) {
        items.remove(item);
    }

    /**
     *
     * @param item: picked item that they will pick up
     */
    public void addToInventory(Item item) {
        items.add(item);            // felvesszük a tárgyat az inventoryba
    }

    /** only profs
     *
     */
    public abstract void paralyze();        // Ennek dőltnek kellene lennie a modellen?

    /** only undergrads
     *
     */
    public abstract void dropout();         // Ennek dőltnek kell lennie a modellen?
    /**
     *
     * @param room: chosen room where they move
     */
    public void moveTo(Room room) {
        if (where != null)
            where.removeOccupant(this);

        where = room;
        room.addOccupant(this);
    }

    /** player chose to activate this protection
     *
     */
    public void useFFP2() {
        ffp2Countdown = 3;
    }

    /**
     *
     * @param index: identifier of item they want to pick UP
     */
    private boolean pickItem(int index) {
        if (where.getItem(index) == null) {
            return false;
        }
        else {
            where.getItem(index).pickup(this);
            return true;
        }
    }

    /**
     *
     * @param index: identifier of item they want to put down
     */
    private boolean disposeItem(int index) {
        Item item = getItem(index);
        if(item == null) {
            return false;
        }
        else {
            where.addItem(item);
            return true;
        }
    }

    private boolean moveTowards (Direction direction) {
        Room r;

        if ((r = where.isAccessible(direction)) != null) {
            if (r.canAccept()) {
                moveTo(r);
                return true;
            }
        }

        return false;
    }

    public void dropItems() {
        for (Item item : items)
            item.dispose();
    }
    public void scheduleDrop() {}           // EZ A TERVBEN NINCS BENNE
    private boolean consoleAct() {
        HashMap<Character, Function<Integer, Boolean>> cmds = new HashMap<>();
        cmds.put('p', this::pickItem);
        cmds.put('u', this::useItem);
        cmds.put('d', this::disposeItem);
        HashMap<Character, String> man = new HashMap<>();
        man.put('p', "Pick item at given room item index");
        man.put('u', "Use item at given inventory item index");
        man.put('d', "Dispose item at given inventory item index");
        man.put('a', "Abort");
        Scanner scnr = new Scanner(System.in);

        while (true) {
            logger.fine("Choose command:");

            for (Map.Entry<Character, String> e : man.entrySet())
                logger.fine(() -> String.format("Cmd: %c, %s\n", e. getKey(), e.getValue()));

            Character cmd = scnr.nextLine().charAt(0);

            if (man.getOrDefault(cmd, null) == null)
                logger.fine(commandNotRecognizedMsg);
            else if (cmd == 'a')
                return false;
            else {
                logger.fine("Supply parameter:");

                try {
                    int p = Integer.parseInt(scnr.nextLine());

                    if (!cmds.get(cmd).apply(p))
                        logger.fine("Act failed");
                    else return true;
                }
                catch (Exception ex) {
                    logger.fine("Parameter is invalid");
                }
            }
        }
    }
    private boolean consoleMove() {
        HashMap<String, Direction> dirs = new HashMap<>();
        HashMap<String, String> man = new HashMap<>();

        for (Direction d : Direction.values()) {
            String name = d.name();

            String cmd = IntStream.rangeClosed(0, d.name().length() - 1)
                    .filter(i -> i == 0 || d.name().charAt(i - 1) == '_')
                    .mapToObj(name::charAt).map(Object::toString)
                    .collect(Collectors.joining())
                    .toLowerCase();

            dirs.put(cmd, d);
            man.put(cmd, "Move " + d.name());
        }

        man.put("a", "abort");
        Scanner scnr = new Scanner(System.in);

        while (true) {
            logger.fine("Choose a command:");

            for (Map.Entry<String, String> e : man.entrySet())
                logger.fine(()->String.format("Cmd: %s, %s\n", e.getKey(), e.getValue()));

            String cmd = scnr.nextLine();

            if (man.getOrDefault(cmd, null) == null)
                logger.fine(commandNotRecognizedMsg);
            else if (cmd.equals("a"))
                return false;
            else if (!moveTowards(dirs.get(cmd)))
                logger.fine("Could not move in direction");
            else return true;
        }
    }
    private void consoleMoveTowards(Direction direction) {}
    protected abstract boolean useItem(int index);      // Ez nincs kifejtve a tervbem


    // getters and setters
    /**
     *
     * @return room: identifier of currently occupied room (by this player)
     */
    public Room where() { return where; }
    public List<Item> getItems() { return items; }     // ehhez setter nem kell
    public Game getGame() { return game; }        // ez protected volt (miert?)

    @Override
    public abstract String toString();


}
