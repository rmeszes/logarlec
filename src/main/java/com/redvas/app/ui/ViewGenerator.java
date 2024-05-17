package com.redvas.app.ui;

import com.redvas.app.Game;
import com.redvas.app.items.*;
import com.redvas.app.map.Door;
import com.redvas.app.map.rooms.EnchantedRoom;
import com.redvas.app.map.rooms.ResizingRoom;
import com.redvas.app.map.rooms.Room;
import com.redvas.app.players.Janitor;
import com.redvas.app.players.Professor;
import com.redvas.app.players.Undergraduate;
import com.redvas.app.ui.items.*;
import com.redvas.app.ui.players.JanitorView;
import com.redvas.app.ui.players.ProfessorView;
import com.redvas.app.ui.players.UndergraduateView;
import com.redvas.app.ui.rooms.*;

public class ViewGenerator implements GeneratorListener {
    private RoomView[][] rooms;

    private Game game;
    public Game getGame() { return game; }

    private final GamePanel gp;

    public ViewGenerator(int width, int height, int players, GamePanel gp) {
        this.gp = gp;
        gp.setLayout(null);
        rooms = new RoomView[height][width];
        game = new Game(width, height, players, this);
    }

    @Override
    public void enchantedRoomCreated(EnchantedRoom er, int x, int y) {
    }

    @Override
    public void resizingRoomCreated(ResizingRoom rr, int x, int y){
    }
    @Override
    public void roomCreated(Room room, int x, int y) {
        rooms[y][x] = new RoomView(room, x, y); // beallitja a sajat boundjait a panelen belul
        gp.add(rooms[y][x]);
    }

    @Override
    public void doorCreated(Door door, int room1x, int room1y, int room2x, int room2y) {
        DoorView dv = new DoorView(door, room1x, room1y, room2x, room2y);  // beallitja a sajat boundjait a panelen belul
        gp.add(dv);
    }

    @Override
    public void professorCreated(Professor er, int x, int y) {
        rooms[y][x].addOccupant(new ProfessorView(er));
    }

    @Override
    public void undergraduateCreated(Undergraduate undergraduate, int x, int y) {
        rooms[y][x].addOccupant(new UndergraduateView(undergraduate));
    }

    @Override
    public void janitorCreated(Janitor janitor, int x, int y) {
        rooms[y][x].addOccupant(new JanitorView(janitor));
    }

    @Override
    public void logarlecCreated(Logarlec l, int x, int y) {
        new LogarlecView(l, x, y);
    }

    @Override
    public void airFreshenerCreated(AirFreshener a, int x, int y) {
        new AirFreshenerView(a, x, y);
    }

    @Override
    public void rottenCamembertCreated(RottenCamembert rottenCamembert, int x, int y) {
        new RottenCamembertView(rottenCamembert, x, y);
    }

    @Override
    public void holyBeerCreated(HolyBeer holyBeer, int x, int y) {
        new HolyBeerView(holyBeer, x, y);
    }

    @Override
    public void tvszCreated(TVSZ tvsz, int x, int y) {
        new TVSZView(tvsz, x, y);
    }

    @Override
    public void wetWipeCreated(WetWipe wetWipe, int x, int y) {
        new WetWipeView(wetWipe, x, y);
    }

    @Override
    public void transistorCreated(Transistor t, int x, int y) {
        new TransistorView(t, x, y);
    }

    @Override
    public void ffp2Created(FFP2 ffp2, int x, int y) {
        new FFP2View(ffp2, x, y);
    }

    @Override
    public void combinedTransistorCreated(CombinedTransistor ct, int x, int y) {
        new CombinedTransistorView(ct,x, y);
    }
}
