package com.redvas.app.map.rooms;

import com.redvas.app.map.Direction;
import com.redvas.app.map.Door;
import com.redvas.app.map.Labyrinth;

import java.util.Map;

public class EnchantedRoom extends Room {
    @Override
    public void step() {
        super.step();

        for (Map.Entry<Direction, Door> e : doors.entrySet())
            e.getValue().setVanished(!e.getValue().isVanished());
    }

    public EnchantedRoom(Labyrinth labyrinth, Integer id, Integer capacity) {
        super(labyrinth, id, capacity);

    }
}
