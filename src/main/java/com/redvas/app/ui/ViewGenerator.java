package com.redvas.app.ui;

import com.redvas.app.items.*;
import com.redvas.app.map.Door;
import com.redvas.app.map.rooms.EnchantedRoom;
import com.redvas.app.map.rooms.ResizingRoom;
import com.redvas.app.map.rooms.Room;
import com.redvas.app.players.Janitor;
import com.redvas.app.players.Professor;
import com.redvas.app.players.Undergraduate;
import com.redvas.app.ui.rooms.ResizingRoomView;
import com.redvas.app.ui.rooms.*;
import com.redvas.app.ui.players.*;
import com.redvas.app.ui.items.*;

public class ViewGenerator implements GeneratorListener {


    @Override
    public void enchantedRoomCreated(EnchantedRoom er, int x, int y) {
        new EnchantedRoomView(er);
    }

    @Override
    public void resizingRoomCreated(ResizingRoom rr, int x, int y){
        new ResizingRoomView(rr);
    }
    @Override
    public void roomCreated(Room room, int x, int y) {
        new RoomView(room);
    }

    @Override
    public void doorCreated(Door door, int x, int y) {

    }

    @Override
    public void professorCreated(Professor er, int x, int y) {
        new ProfessorView(er);
    }

    @Override
    public void undergraduateCreated(Undergraduate undergraduate, int x, int y) {
        new UndergraduateView(undergraduate);
    }

    @Override
    public void janitorCreated(Janitor janitor, int x, int y) {
        new JanitorView(janitor);
    }

    @Override
    public void logarlecCreated(Logarlec l, int x, int y) {
        new LogarlecView(l);
    }

    @Override
    public void airFreshenerCreated(AirFreshener a, int x, int y) {
        new AirFreshenerView(a);
    }

    @Override
    public void rottenCamembertCreated(RottenCamembert rottenCamembert, int x, int y) {
        new RottenCamembertView(rottenCamembert);
    }

    @Override
    public void holyBeerCreated(HolyBeer holyBeer, int x, int y) {
        new HolyBeerView(holyBeer);
    }

    @Override
    public void tvszCreated(TVSZ tvsz, int x, int y) {
        new TVSZView(tvsz);
    }

    @Override
    public void wetWipeCreated(WetWipe wetWipe, int x, int y) {
        new WetWipeView(wetWipe);
    }

    @Override
    public void transistorCreated(Transistor t, int x, int y) {
        new TransistorView(t);
    }

    @Override
    public void ffp2Created(FFP2 ffp2, int x, int y) {
        new FFP2View(ffp2);
    }
}
