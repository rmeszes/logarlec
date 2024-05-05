package com.redvas.app.map;

import com.redvas.app.map.rooms.Room;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.HashMap;

public class Door {
    // the direction, and the reverse directions are even and odd valued. The even value is preserved only
    private int evenIndex = -1;
    private final Room[] roomMap = new Room[2];
    private final boolean[] passableMap = new boolean[2];

    public Room connectsTo(Direction d) { return roomMap[d.getValue() & 1]; }

    public void setConnection(Direction in, Room to) {
        if (in.getValue() != evenIndex && in.getValue() != evenIndex + 1)
            return;

        roomMap[in.getValue() & 1] = to;
    }

    private boolean isVanished = false;

    public void setVanished(boolean isVanished) {
        this.isVanished = isVanished;
    }

    private HashMap<Direction, Door> selection;
    public void acceptDoors(HashMap<Direction, Door> doors) {
        selection = doors;
    }

    public Element saveXML(Document document) {
        Element door = document.createElement("door");
        door.setAttribute("is_vanished", String.valueOf(isVanished));
        door.setAttribute("even_direction", Direction.valueOf(evenIndex).name()); // odd_direction is not written out to avoid redundancy
        door.setAttribute("even_room", String.valueOf(roomMap[0].getID()));
        door.setAttribute("odd_room", String.valueOf(roomMap[1].getID()));
        door.setAttribute("even_passable", String.valueOf(passableMap[0]));
        door.setAttribute("odd_passable", String.valueOf(passableMap[1]));
        return door;
    }

    public Door(Room from, Room to, Direction in, boolean passable) {
        from.configureDoors(this);
        selection.put(in, this);
        to.configureDoors(this);
        selection.put(in.getReverse(), this);
        setVanished(false);
        setPassable(in, passable);
        setPassable(in.getReverse(), passable);
        evenIndex = Direction.getEvenPairValue(in);
        roomMap[in.getValue() & 1] = to;
        roomMap[in.getReverse().getValue() & 1] = from;
    }

    public Door(Room from, Room to, Direction in, boolean passable2To, boolean passable2From) {
        from.configureDoors(this);
        selection.put(in, this);
        to.configureDoors(this);
        selection.put(in.getReverse(), this);
        setVanished(false);
        setPassable(in, passable2To);
        setPassable(in.getReverse(), passable2From);
        evenIndex = Direction.getEvenPairValue(in);
        roomMap[in.getValue() & 1] = to;
        roomMap[in.getReverse().getValue() & 1] = from;
    }

    public boolean isVanished() { return isVanished; }
    public boolean isPassable(Direction in) { return passableMap[in.getValue() & 1]; }

    public void setPassable(Direction in, boolean isPassable) {
        if (in.getValue() != evenIndex && in.getValue() != evenIndex + 1)
            return;

        passableMap[in.getValue() & 1] = isPassable;
    }
}
