package com.redvas.app.map;

import com.redvas.app.map.rooms.Room;
import com.redvas.app.ui.rooms.DoorChangeListener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Map;

public class Door {
    // the direction, and the reverse directions are even and odd valued. The even value is preserved only
    private int evenIndex = -1;
    private final Room[] roomMap = new Room[2];
    private final boolean[] passableMap = new boolean[2];

    public Room connectsTo(Direction d) {
        if (d.getValue() != evenIndex && d.getValue() !=evenIndex + 1) return null;
        return roomMap[d.getValue() & 1];
    }

    public void setConnection(Direction in, Room to) {
        if (in.getValue() != evenIndex && in.getValue() != evenIndex + 1)
            return;

        roomMap[in.getValue() & 1] = to;
    }

    private boolean isVanished = false;

    public void setVanished(boolean isVanished) {
        this.isVanished = isVanished;
        if (listener != null)
            listener.changed();
    }

    private Map<Direction, Door> selection;

    public void acceptDoors(Map<Direction, Door> doors) {
        selection = doors;
    }

    public Element saveXML(Document document) {
        Element door = document.createElement("door");
        door.setAttribute("is_vanished", String.valueOf(isVanished));
        door.setAttribute("target_direction", Direction.valueOf(evenIndex).name()); // odd_direction is not written out to avoid redundancy
        door.setAttribute("target_room", String.valueOf(roomMap[0].getID()));
        door.setAttribute("origin_room", String.valueOf(roomMap[1].getID()));
        door.setAttribute("towards_target_passable", String.valueOf(passableMap[0]));
        door.setAttribute("towards_origin_passable", String.valueOf(passableMap[1]));
        return door;
    }


    private DoorChangeListener listener = null;
    public void setListener(DoorChangeListener listener) {
        this.listener = listener;
    }

    public Door(Room from, Room to, Direction in, boolean passable) {
        evenIndex = (in.getValue() & 1) == 1 ? in.getValue() - 1 : in.getValue();
        from.configureDoors(this);
        selection.put(in, this);
        to.configureDoors(this);
        selection.put(in.getReverse(), this);
        setVanished(false);
        setPassable(in, passable);
        setPassable(in.getReverse(), passable);
        roomMap[in.getValue() & 1] = to;
        roomMap[in.getReverse().getValue() & 1] = from;
    }

    public Door(Room from, Room to, Direction in, boolean passable2To, boolean passable2From) {
        evenIndex = (in.getValue() & 1) == 1 ? in.getValue() - 1 : in.getValue();
        from.configureDoors(this);
        selection.put(in, this);
        to.configureDoors(this);
        selection.put(in.getReverse(), this);
        setVanished(false);
        setPassable(in, passable2To);
        setPassable(in.getReverse(), passable2From);
        roomMap[in.getValue() & 1] = to;
        roomMap[in.getReverse().getValue() & 1] = from;
    }

    public boolean isVanished() { return isVanished; }
    public boolean isPassable(Direction in) { return passableMap[in.getValue() & 1]; }

    public void setPassable(Direction in, boolean isPassable) {
        if (in.getValue() != evenIndex && in.getValue() != evenIndex + 1)
            return;

        passableMap[in.getValue() & 1] = isPassable;
        if (listener != null)
            listener.changed();
    }
}
