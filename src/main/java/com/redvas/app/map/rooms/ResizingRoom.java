package com.redvas.app.map.rooms;

import com.redvas.app.App;
import com.redvas.app.map.Direction;
import com.redvas.app.map.Door;
import com.redvas.app.map.Labyrinth;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Random;
import java.util.logging.Logger;

public class ResizingRoom extends Room {
    private static final Random random = new Random();

    @Override
    public void step() {
        super.step();

        if (isMerged()) split();
        else expand();
    }

    @Override
    public Element saveXML(Document document) {
        Element resizingRoom = super.saveXML(document);
        resizingRoom.setAttribute("merge_d", String.valueOf(mergeD));
        resizingRoom.setAttribute("merge_direction", mergeDirection.name());
        resizingRoom.setAttribute("incorporated_id", String.valueOf(incorporatedId));
        return resizingRoom;
    }

    private boolean isMerged() {
        return doors.getOrDefault(Direction.BOTTOM_RIGHT, null) != null;
    }
    private boolean mergeD = false;
    private int incorporatedId = -1;
    private Direction mergeDirection;
    @Override
    protected boolean incorporatable() { return false; }
    private static final Logger logger = App.getConsoleLogger(Room.class.getName());

    @Override
    public void loadXML(Element room) {
        super.loadXML(room);
        mergeD = Boolean.parseBoolean(room.getAttribute("merge_d"));
        incorporatedId = Integer.parseInt(room.getAttribute("incorporated_id"));
        mergeDirection = Direction.valueOf(room.getAttribute("merge_direction"));
    }

    public ResizingRoom(Integer id, Labyrinth labyrinth, Integer capacity, Direction mergeDirection) {
        super(labyrinth, id,capacity);
        this.mergeDirection = mergeDirection;
    }

    public void split() {
        if (!isMerged()) return;

        if (!occupants.isEmpty()) return;

        Room r = new Room(labyrinth, incorporatedId, random.nextInt(2, 6));
        labyrinth.remember(r);

        if (doors.getOrDefault(Direction.RIGHT, null) == null) {
            // Vertical alignment
            // Let's newly created room be on TOP
            r.doors.put(Direction.LEFT, doors.get(Direction.TOP_LEFT));
            r.doors.put(Direction.RIGHT, doors.get(Direction.TOP_LEFT));
            r.doors.put(Direction.UP, doors.get(Direction.UP));
            doors.remove(Direction.TOP_LEFT);
            doors.remove(Direction.BOTTOM_RIGHT);

            doors.put(Direction.UP, new Door(r, mergeD));
            r.doors.put(Direction.DOWN, new Door(this, !mergeD));

            doors.put(Direction.LEFT, doors.get(Direction.BOTTOM_LEFT));
            doors.put(Direction.RIGHT, doors.get(Direction.BOTTOM_RIGHT));
            doors.remove(Direction.BOTTOM_LEFT);
            doors.remove(Direction.BOTTOM_RIGHT);
        }
        else {
            // Horizontal alignment
            // Let's newly created room be to the LEFT
            r.doors.put(Direction.LEFT, doors.get(Direction.LEFT));
            r.doors.put(Direction.UP, doors.get(Direction.TOP_LEFT));
            r.doors.put(Direction.DOWN, doors.get(Direction.BOTTOM_LEFT));
            doors.remove(Direction.TOP_LEFT);
            doors.remove(Direction.BOTTOM_LEFT);

            doors.put(Direction.UP, doors.get(Direction.TOP_RIGHT));
            doors.put(Direction.DOWN, doors.get(Direction.BOTTOM_RIGHT));
            doors.remove(Direction.TOP_RIGHT);
            doors.remove(Direction.BOTTOM_RIGHT);

            doors.put(Direction.LEFT, new Door(r, mergeD));
            r.doors.put(Direction.RIGHT, new Door(this, !mergeD));
        }

        mergeD = !mergeD;
    }

    public void expand() {
        logger.fine("Setting this room's attributes to new ones..");

        Door door = null;

        if ((door = doors.getOrDefault(mergeDirection, null)) == null) return;

        if (!door.isPassable() && !door.connectsTo().doors.get(reverseDirections.get(mergeDirection)).isPassable())
            return;

        Room room = door.connectsTo();

        if (isMerged() || !room.incorporatable()) return;

        if (!occupants.isEmpty() || !room.occupants.isEmpty()) return;

        if (mergeDirection == Direction.UP) {
            doors.put(Direction.TOP_LEFT, room.doors.get(Direction.LEFT));

            Door d;

            if ((d = room.doors.get(Direction.LEFT)) != null)
                d.connectsTo().doors.get(Direction.RIGHT).setConnection(this);

            doors.put(Direction.TOP_RIGHT, room.doors.get(Direction.RIGHT));

            if ((d = room.doors.get(Direction.RIGHT)) != null)
                d.connectsTo().doors.get(Direction.LEFT).setConnection(this);

            doors.put(Direction.BOTTOM_LEFT, doors.get(Direction.LEFT));
            doors.put(Direction.BOTTOM_RIGHT, doors.get(Direction.RIGHT));

            doors.put(Direction.LEFT, null);
            doors.put(Direction.RIGHT, null);

            doors.put(Direction.UP, room.doors.get(Direction.UP));

            if ((d = room.doors.get(Direction.UP)) != null)
                d.connectsTo().doors.get(Direction.DOWN).setConnection(this);

        }
        else if (mergeDirection == Direction.LEFT) {
            doors.put(Direction.TOP_LEFT, room.doors.get(Direction.UP));

            Door d;

            if ((d = room.doors.get(Direction.UP)) != null)
                d.connectsTo().doors.get(Direction.DOWN).setConnection(this);

            doors.put(Direction.TOP_RIGHT, doors.get(Direction.UP));
            doors.put(Direction.BOTTOM_LEFT, room.doors.get(Direction.DOWN));

            if ((d = room.doors.get(Direction.DOWN)) != null)
                d.connectsTo().doors.get(Direction.UP).setConnection(this);

            doors.put(Direction.BOTTOM_RIGHT, doors.get(Direction.DOWN));

            doors.put(Direction.LEFT, room.doors.get(Direction.LEFT));

            if ((d = room.doors.get(Direction.LEFT)) != null)
                d.connectsTo().doors.get(Direction.RIGHT).setConnection(this);


            doors.put(Direction.UP, null);
            doors.put(Direction.DOWN, null);
        }
        else if (mergeDirection == Direction.DOWN) {
            doors.put(Direction.TOP_LEFT, doors.get(Direction.LEFT));
            doors.put(Direction.TOP_RIGHT, doors.get(Direction.RIGHT));

            doors.put(Direction.BOTTOM_LEFT, room.doors.get(Direction.LEFT));

            Door d;

            if ((d = room.doors.get(Direction.LEFT)) != null)
                d.connectsTo().doors.get(Direction.RIGHT).setConnection(this);

            doors.put(Direction.BOTTOM_RIGHT, room.doors.get(Direction.RIGHT));

            if ((d = room.doors.get(Direction.RIGHT)) != null)
                d.connectsTo().doors.get(Direction.LEFT).setConnection(this);

            doors.put(Direction.LEFT, null);
            doors.put(Direction.RIGHT, null);

            doors.put(Direction.DOWN, room.doors.get(Direction.DOWN));

            if ((d = room.doors.get(Direction.DOWN)) != null)
                d.connectsTo().doors.get(Direction.UP).setConnection(this);
        }
        else if (mergeDirection == Direction.RIGHT) {
            doors.put(Direction.TOP_LEFT, doors.get(Direction.UP));
            doors.put(Direction.TOP_RIGHT, room.doors.get(Direction.UP));

            Door d;

            if ((d = room.doors.get(Direction.UP)) != null)
                d.connectsTo().doors.get(Direction.DOWN).setConnection(this);

            doors.put(Direction.BOTTOM_LEFT, doors.get(Direction.DOWN));
            doors.put(Direction.BOTTOM_RIGHT, room.doors.get(Direction.DOWN));

            if ((d = room.doors.get(Direction.DOWN)) != null)
                d.connectsTo().doors.get(Direction.UP).setConnection(this);

            doors.put(Direction.RIGHT, room.doors.get(Direction.RIGHT));

            if ((d = room.doors.get(Direction.RIGHT)) != null)
                d.connectsTo().doors.get(Direction.LEFT).setConnection(this);

            doors.put(Direction.UP, null);
            doors.put(Direction.DOWN, null);
        }

        room.destroy();
    }
}
