package com.redvas.app.players;

import com.redvas.app.App;
import com.redvas.app.Game;
import com.redvas.app.Steppable;
import com.redvas.app.items.Item;
import com.redvas.app.map.Direction;
import com.redvas.app.map.rooms.Room;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.*;
import java.util.function.Function;
import java.util.logging.Logger;

//absztrakt class, majd az implementációk lesznek tesztelve
public abstract class Player implements Steppable {
    private final int id;
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
    private static final String COMMAND_NOT_RECOGNIZED_MSG = "Command not recognised.";

    // tagváltozók
    protected Room where;
    protected final List<Item> items;
    protected int faintCountdown;
    protected int ffp2Countdown  = 0;
    protected final Game game;

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
     * @return item that they picked
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
        if (ffp2Countdown == 0) {
            logger.fine("Undergraduate has fainted and dropped items");
            faintCountdown = 3;
            dropItems();
        }
    }



    /**
     *
     * @param item: picked item that they will dispose of
     */
    public void removeFromInventory(Item item) {
        // ilyet gondolom nem lehet, de a tranzisztor merge-nél csak az egyiket remove-olta az eredeti
        for (int i = 0; i < items.size(); i++){
            if (items.get(i).toString().equals(item.toString())) {
                items.remove(i);
                break;
            }
        }
        // items.remove(item);
    }

    /**
     *
     * @param item: picked item that they will pick up
     */
    public void addToInventory(Item item) {

        if(items.size() < 5) { //ha van hely az inventoryban
            items.add(item);            // felvesszük a tárgyat az inventoryba
        }
        else{
            logger.fine("Inventory full");
        }
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
        if (where.getItem(index - 1) == null) { // -1 mert a kiírásokkor 1-5 ig vannak itemek, a list meg 0-tól kezd
            return false;
        }
        else {
            where.getItem(index - 1).pickup(this);
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
            item.dispose();
            logger.fine("Player has disposed of an item.");
            return true;
        }
    }

    private boolean moveTowards (Direction direction) {
        Room r;

        if ((r = where.isAccessible(direction)) != null && r.canAccept()) {
                moveTo(r);
                return true;
            }

        game.getGamePanel().repaint();
        return false;
    }

    public void dropItems() {
        while (!items.isEmpty())
            items.get(items.size() - 1).dispose();
    }
    public void scheduleDrop() {}           // EZ A TERVBEN NINCS BENNE

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

    protected boolean consoleAct() {
        HashMap<String, Function<Integer, Boolean>> cmds = new HashMap<>();
        cmds.put("pickup", this::pickItem);
        cmds.put("use", this::useItem);
        cmds.put("dispose", this::disposeItem);
        HashMap<String, String> man = new HashMap<>();
        man.put("pickup", "Pick item at given room item index");
        man.put("use", "Use item at given inventory item index");
        man.put("dispose", "Dispose item at given inventory item index");
        man.put("abort", "Abort");
        Scanner scanner = App.reader;

        while (true) {
            StringBuilder builder = new StringBuilder();
            builder.append("Choose command:\n");

            for (Map.Entry<String, String> e : man.entrySet())
                builder.append(String.format("%s, %s%n", e. getKey(), e.getValue()));

            logger.info(builder::toString);
            String cmd = scanner.nextLine().trim();

            if (man.getOrDefault(cmd, null) == null)
                logger.fine(COMMAND_NOT_RECOGNIZED_MSG);
            else if (Objects.equals(cmd, "abort"))
                return false;
            else {
                logger.fine("Supply parameter:");

                try {
                    int p = Integer.parseInt(scanner.nextLine());

                    if (Boolean.FALSE.equals(cmds.get(cmd).apply(p)))
                        logger.fine("Act failed");
                    else return true;
                }
                catch (Exception ex) {
                    logger.fine("Parameter is invalid");
                }
            }
        }
    }
    protected boolean consoleMove() {
        HashMap<String, Direction> dirs = new HashMap<>();
        Set<String> man = new HashSet<>();

        for (Direction d : Direction.values()) {
            String cmd = d.name().toLowerCase();

            dirs.put(cmd, d);
            man.add(cmd);
        }

        man.add("abort");
        Scanner scanner = App.reader;

        while (true) {
            StringBuilder builder = new StringBuilder();
            builder.append("Enter a direction or write abort:\n");

            Set<String> availableDirections = new HashSet<>();

            for(Direction d : where.getAccessibleDirections()) {
                availableDirections.add(d.name().toLowerCase());
            }

            for (String s : man)
                if(availableDirections.contains(s))
                    builder.append(String.format("%s%n", s));

            logger.fine(builder::toString);

            String cmd = scanner.nextLine().trim();

            if (Boolean.FALSE.equals(man.contains(cmd)))
                logger.fine(COMMAND_NOT_RECOGNIZED_MSG);
            else if (cmd.equals("abort"))
                return false;
            else if (!moveTowards(dirs.get(cmd)))
                logger.fine("Could not move in direction");
            else {
                logger.fine("Player has moved to another room.");
                game.getGamePanel().repaint();
                return true;
            }
        }
    }
    protected void consoleMoveTowards(Direction direction) {}

    /**
     * Moves to a random neighbouring room that has space or does nothing
     * @return Returns the room they moved to or null.
     */
    protected Room randomMove() {
        Set<Room> rooms = where().getAccessibleRooms();
        for(Room room : rooms) {
            if(Boolean.TRUE.equals(room.canAccept())) {
                logger.fine("Moving to: Room id: "+room.getID());
                moveTo(room);
                return room;
            }
        }
        game.getGamePanel().repaint();
        return null;
    }

    /** two possible outcomes: 2 transistors merged
     * or nothing happens because player did not have 2 transistors
     *
     */
    public void mergeItems(int i1, int i2) {} //ez ide kell, mert a transistor Player-t kap

    protected Boolean consoleList() {
        StringBuilder builder = new StringBuilder();
        builder.append("Items in inventory:\n");
        if(getItems().isEmpty())
            builder.append("None.\n");
        else{
            int i = 1;
            for (Item item : items)
                builder.append(i++).append(". ").append(item.toString()).append('\n');
        }


        builder.append("Items in room:\n");
        if(where().getItems().isEmpty())
            builder.append("Room has no items\n");
        else {
            int i = 1;
            for (Item item : where.getItems())
                builder.append(i++).append(". ").append(item.toString()).append('\n');
        }

        builder.append("Accessible rooms: (directions)\n");

        if(where().getAccessibleDirections().isEmpty())
            builder.append("No accessible rooms\n");
        else {
            for(Direction direction : where.getAccessibleDirections()) {
                builder.append(direction.toString()).append('\n');
            }
        }
        logger.fine(builder::toString);
        return false;
    }
}
