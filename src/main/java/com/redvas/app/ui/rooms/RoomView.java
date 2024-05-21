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

    private BufferedImage floorImage;               // alap
    private BufferedImage floorImageWhenGaseous;    // amikor gázossá válik
    private BufferedImage floorImageWhenSticky;     // amikor ragadós a szoba
    private BufferedImage floorImageWhenEnchanted;
    public RoomView(Room r, int x, int y) {
        r.setListener(this);
        setLayout(null);
        this.room = r;
        setBounds(x * SIZE, y * SIZE, SIZE, SIZE);

        try {
            floorImage = ImageIO.read(new File("src/main/resources/floor.png"));
            floorImageWhenGaseous = ImageIO.read(new File("src/main/resources/map/gaseous_room1000.png"));
            floorImageWhenSticky = ImageIO.read(new File("src/main/resources/map/sticky_room1000.png"));
            floorImageWhenEnchanted = ImageIO.read(new File("src/main/resources/map/enchanted_room1000.png"));
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
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
        repaint();
        repaintDoors();
    }

    private void repaintDoors() {
        for (DoorView dv : doors)
            SwingUtilities.invokeLater(dv::repaint);

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
        this.isSticky = isSticky;
    }

    @Override
    public void roomGaseousnessChange(boolean isGaseous) {
        this.isGaseous = isGaseous;

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

        if (!isGaseous && !isSticky) {      // alap
            g.drawImage(floorImage, 0, 0, SIZE, SIZE, null);
        }

        else if (!isGaseous) {  // csak ragad
            g.drawImage(floorImageWhenSticky, 0, 0, SIZE, SIZE, null);
        }

        else if (!isSticky) {  // csak gázos
            g.drawImage(floorImageWhenGaseous, 0, 0, SIZE, SIZE, null);
        }

        else  {  // elvarázsolt
            g.drawImage(floorImageWhenEnchanted, 0, 0, SIZE, SIZE, null);
        }
    }
}
