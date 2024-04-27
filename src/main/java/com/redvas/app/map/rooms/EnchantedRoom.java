package com.redvas.app.map.rooms;

import com.redvas.app.map.Direction;
import com.redvas.app.map.Door;
import com.redvas.app.map.Labyrinth;

import java.util.Map;

public class EnchantedRoom extends Room {
    @Override
    public void step() {
        super.step();

        for (Map.Entry<Direction, Door> e : doors.entrySet()) {
            e.getValue().setVanished(!e.getValue().isVanished());
            e.getValue().connectsTo().doors.get(reverseDirections.get(e.getKey())).setVanished(e.getValue().isVanished());
        }
    }

    public EnchantedRoom(Labyrinth labyrinth, Integer id) {
        super(labyrinth, id);
    }
}