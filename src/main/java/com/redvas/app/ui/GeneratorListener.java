package com.redvas.app.ui;

import com.redvas.app.items.*;
import com.redvas.app.map.Door;
import com.redvas.app.map.rooms.EnchantedRoom;
import com.redvas.app.map.rooms.ResizingRoom;
import com.redvas.app.map.rooms.Room;
import com.redvas.app.players.Janitor;
import com.redvas.app.players.Professor;
import com.redvas.app.players.Undergraduate;

public interface GeneratorListener {
    void enchantedRoomCreated(EnchantedRoom er, int x, int y);
    void resizingRoomCreated(ResizingRoom rr, int x, int y);
    void roomCreated(Room room, int x, int y);
    void doorCreated(Door door, int x, int y);
    void professorCreated(Professor er, int x, int y);
    void undergraduateCreated(Undergraduate undergraduate, int x, int y);
    void janitorCreated(Janitor janitor, int x, int y);
    void logarlecCreated(Logarlec l, int x, int y);
    void airFreshenerCreated(AirFreshener a, int x, int y);
    void rottenCamembertCreated(RottenCamembert rottenCamembert, int x, int y);
    void holyBeerCreated(HolyBeer holyBeer, int x, int y);
    void tvszCreated(TVSZ tvsz, int x, int y);
    void wetWipeCreated(WetWipe wetWipe, int x, int y);
    void transistorCreated(Transistor t, int x, int y);
    void ffp2Created(FFP2 ffp2, int x, int y);
    void combinedTransistorCreated(CombinedTransistor ct, int x, int y);
}
