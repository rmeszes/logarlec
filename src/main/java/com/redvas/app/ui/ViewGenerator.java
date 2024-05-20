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
import com.redvas.app.ui.rooms.DoorView;
import com.redvas.app.ui.rooms.RoomView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ViewGenerator implements GeneratorListener {
    private RoomView[][] rooms;
    private ArrayList<DoorView>[][] doors;

    private Game game;
    public Game getGame() { return game; }

    private final GamePanel gp;

    public ViewGenerator(int width, int height, int players, GamePanel gp) {
        this.gp = gp;
        gp.setLayout(null);
        rooms = new RoomView[height][width];
        doors = (ArrayList<DoorView>[][]) Array.newInstance(ArrayList.class, height, width);

        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                doors[y][x] = new ArrayList<>();

        game = new Game(width, height, players, this);

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                for (DoorView dv : doors[i][j])
                    rooms[i][j].addDoor(dv);
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
        doors[room1y][room1x].add(dv);
        doors[room2y][room2y].add(dv);
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
         rooms[y][x].addItem(new LogarlecView(l));
    }

    @Override
    public void airFreshenerCreated(AirFreshener a, int x, int y) {
        rooms[y][x].addItem(new AirFreshenerView(a));
    }

    @Override
    public void rottenCamembertCreated(RottenCamembert rottenCamembert, int x, int y) {
        rooms[y][x].addItem(new RottenCamembertView(rottenCamembert));
    }

    @Override
    public void holyBeerCreated(HolyBeer holyBeer, int x, int y) {
        rooms[y][x].addItem(new HolyBeerView(holyBeer));
    }

    @Override
    public void tvszCreated(TVSZ tvsz, int x, int y) {
        rooms[y][x].addItem(new TVSZView(tvsz));
    }

    @Override
    public void wetWipeCreated(WetWipe wetWipe, int x, int y) {
        rooms[y][x].addItem(new WetWipeView(wetWipe, x, y));
    }

    @Override
    public void transistorCreated(Transistor t, int x, int y) {
        rooms[y][x].addItem(new TransistorView(t));
    }

    @Override
    public void ffp2Created(FFP2 ffp2, int x, int y) {
        rooms[y][x].addItem(new FFP2View(ffp2));
    }

    @Override
    public void combinedTransistorCreated(CombinedTransistor ct, int x, int y) {
        rooms[y][x].addItem(new CombinedTransistorView(ct));
    }
}
