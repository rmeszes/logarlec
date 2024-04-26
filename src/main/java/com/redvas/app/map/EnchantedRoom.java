package com.redvas.app.map;

import java.util.Map;

public class EnchantedRoom extends Room {
    @Override
    public void step() {
        super.step();

        for (Map.Entry<Direction, Door> e : doors.entrySet()) {
            e.getValue().setVanished(!e.getValue().isVanished());
        }
    }
    public EnchantedRoom(Labyrinth labyrinth) {
        super(labyrinth);
    }
}
