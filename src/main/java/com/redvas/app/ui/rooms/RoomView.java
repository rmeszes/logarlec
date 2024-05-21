package com.redvas.app.ui.rooms;

import com.redvas.app.App;
import com.redvas.app.items.Item;
import com.redvas.app.map.rooms.Room;
import com.redvas.app.players.Player;
import com.redvas.app.ui.GamePanel;
import com.redvas.app.ui.items.ItemChangeListener;
import com.redvas.app.ui.items.ItemsView;
import com.redvas.app.ui.players.PlayerView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RoomView extends JPanel implements RoomChangeListener {
    private static final Logger logger = App.getConsoleLogger(RoomView.class.getName());
    private final Room room;
    private boolean isSticky = false;       // alapból legyen hamis, de am ez nem int a modellben??
    private boolean isGaseous = false;

    private BufferedImage myImage;
    private static BufferedImage floorImage;               // alap
    private static BufferedImage floorImageWhenGaseous;    // amikor gázossá válik
    private static BufferedImage floorImageWhenSticky;     // amikor ragadós a szoba
    private static BufferedImage floorImageWhenStickyAndGaseous; // amikor mindkettő

    {
        String root = System.getProperty("user.dir");

        try {
            floorImage = ImageIO.read(new File(root + "/src/main/resources/map/basic_room.png"));
            floorImageWhenSticky = ImageIO.read(new File(root + "/src/main/resources/map/basic_sticky_room.png"));
            floorImageWhenGaseous = ImageIO.read(new File(root + "/src/main/resources/map/basic_gaseous_room.png"));
            floorImageWhenStickyAndGaseous = ImageIO.read(new File(root + "/src/main/resources/map/basic_gaseous_sticky_room.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public RoomView(Room r, int x, int y) {
        myImage = floorImage;
        r.setListener(this);
        setLayout(null);
        this.room = r;
        setBounds(x * SIZE, y * SIZE, SIZE, SIZE);
    }

    private final List<PlayerView> occupants = new ArrayList<>();
    private final List<ItemsView> items = new ArrayList<>();

    public void addItem(ItemsView iv){
        items.add(iv);
        int roomLocalY = (items.size() - 1) / 3;
        int roomLocalX = (items.size() - 1) % 3;
        iv.occupyRoomPosition(roomLocalX, roomLocalY);
        add(iv);
    }
    public void removeItem(ItemsView iv){
        items.remove(iv);
    }

    private List<DoorView> doors = new ArrayList<>();

    public void repaintCorrectly() {
        SwingUtilities.invokeLater(() -> paintImmediately(getBounds()));
        repaintDoors();
    }

    private void repaintDoors() {
        for (DoorView dv : doors)
            SwingUtilities.invokeLater(() -> dv.paintImmediately(dv.getBounds()));
    }

    public void addDoor(DoorView dv) {
        doors.add(dv);
    }

    private void reorderOccupants() {
        for (int i = 0; i < occupants.size(); i++) {
            int roomLocalY = i / 3;
            int roomLocalX = i % 3;
            occupants.get(i).occupyRoomPosition(roomLocalX, roomLocalY);
        }

        repaintCorrectly();
    }

    public void addOccupant(PlayerView p) {
        occupants.add(p);
        int roomLocalY = (occupants.size() - 1) / 3;
        int roomLocalX = (occupants.size() - 1) % 3;
        add(p);
        p.occupyRoomPosition(roomLocalX, roomLocalY);
        repaintCorrectly();
    }

    @Override
    public void roomStickinessChange(boolean isSticky) {    // Amikor a modellben változik, akkor ezt kell hívni és ez updateli a view-t
        if (isSticky) {
            if (isGaseous)
                myImage = floorImageWhenStickyAndGaseous;
            else
                myImage = floorImageWhenSticky;
        }
        else myImage = floorImage;
    }

    @Override
    public void roomGaseousnessChange(boolean isGaseous) {
        if (isGaseous) {
            if (isSticky)
                myImage = floorImageWhenStickyAndGaseous;
            else
                myImage = floorImageWhenGaseous;
        }
        else myImage = floorImage;
    }

    private static PlayerView activeLeavingPlayer = null;

    @Override
    public void occupantLeft(Player p) {
        for (PlayerView pw : occupants)
            if (pw.getPlayer() == p) {
                remove(pw);
                occupants.remove(pw);
                activeLeavingPlayer = pw;
                break;
            }

        reorderOccupants();
    }

    @Override
    public void occupantEntered(Player p) {
        if (activeLeavingPlayer == null) return;
        addOccupant(activeLeavingPlayer);
        activeLeavingPlayer = null;
    }

    private static ItemsView activeItem = null;

    public static final int SIZE = (int)(100 * GamePanel.getMagnification());

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(myImage, 0, 0, SIZE, SIZE, null);
    }
}
