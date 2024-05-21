package com.redvas.app.ui.rooms;

import com.redvas.app.App;
import com.redvas.app.map.rooms.Room;
import com.redvas.app.players.Player;
import com.redvas.app.ui.GamePanel;
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
    protected boolean isSticky = false;       // alapból legyen hamis, de am ez nem int a modellben??
    protected boolean isGaseous = false;

    protected BufferedImage myImage,
            basic,
            basicGaseous,
            basicSticky,
            basicStickyGaseous,
            enchanted,
            enchantedSticky,
            enchantedGaseous,
            enchantedStickyGaseous,
            horizontal, vertical,
            horizontalGaseous, verticalGaseous,
            horizontalSticky, verticalSticky,
            horizontalStickyGaseous, verticalStickyGaseous;

    {
        String root = System.getProperty("user.dir");

        try {
            basic = ImageIO.read(new File(root + "/src/main/resources/map/basic_room.png"));
            basicSticky = ImageIO.read(new File(root + "/src/main/resources/map/basic_sticky_room.png"));
            basicGaseous = ImageIO.read(new File(root + "/src/main/resources/map/basic_gaseous_room.png"));
            basicStickyGaseous = ImageIO.read(new File(root + "/src/main/resources/map/basic_sticky_gaseous_room.png"));
            enchanted = ImageIO.read(new File(root + "/src/main/resources/map/enchanted_room.png"));
            enchantedGaseous = ImageIO.read(new File(root + "/src/main/resources/map/enchanted_gaseous_room.png"));
            enchantedSticky = ImageIO.read(new File(root + "/src/main/resources/map/enchanted_sticky_room.png"));
            enchantedStickyGaseous = ImageIO.read(new File(root + "/src/main/resources/map/enchanted_sticky_gaseous_room.png"));
            horizontal = ImageIO.read(new File(root + "/src/main/resources/map/horizontal_room.png"));
            horizontalGaseous = ImageIO.read(new File(root + "/src/main/resources/map/horizontal_gaseous_room.png"));
            horizontalSticky = ImageIO.read(new File(root + "/src/main/resources/map/horizontal_sticky_room.png"));
            horizontalStickyGaseous = ImageIO.read(new File(root + "/src/main/resources/map/horizontal_sticky_gaseous_room.png"));
            vertical = ImageIO.read(new File(root + "/src/main/resources/map/horizontal_room.png"));
            verticalGaseous = ImageIO.read(new File(root + "/src/main/resources/map/vertical_gaseous_room.png"));
            verticalSticky = ImageIO.read(new File(root + "/src/main/resources/map/vertical_sticky_room.png"));
            verticalStickyGaseous = ImageIO.read(new File(root + "/src/main/resources/map/vertical_sticky_gaseous_room.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void updateImage() {
        if (isSticky) {
            if (isGaseous) myImage = basicStickyGaseous;
            else myImage = basicSticky;
        }
        else {
            if (isGaseous) myImage = basicGaseous;
            else myImage = basic;
        }

        repaintCorrectly();
    }

    public RoomView(Room r, int x, int y) {
        r.setListener(this);
        setLayout(null);
        this.room = r;
        setBounds(x * SIZE, y * SIZE, SIZE, SIZE);
        updateImage();
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
        this.isSticky = isSticky;
        updateImage();
    }

    @Override
    public void roomGaseousnessChange(boolean isGaseous) {
        this.isGaseous = isGaseous;
        updateImage();
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
