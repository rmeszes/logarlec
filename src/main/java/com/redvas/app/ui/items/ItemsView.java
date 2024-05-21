package com.redvas.app.ui.items;

import com.redvas.app.items.Item;
import com.redvas.app.ui.GameMenu;
import com.redvas.app.ui.UITool;
import com.redvas.app.ui.rooms.RoomView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class ItemsView extends JPanel implements ItemChangeListener{
    protected BufferedImage itemImage;
    protected static final BufferedImage AirFreshenerImage;
    protected static final BufferedImage CombinedTransistorImage;
    protected static final BufferedImage FFP2Image;
    protected static final BufferedImage HolyBeerImage;
    protected static final BufferedImage LogarlecImage;
    protected static final BufferedImage RottenCamembertImage;
    protected static final BufferedImage TransistorImage;
    protected static final BufferedImage TVSZImage;
    protected static final BufferedImage WetWipeImage;

    private final Item item;

    public Item getItem() {
        return item;
    }

    protected ItemsView(Item item) {
        setOpaque(false);
        this.item = item;
    }

    static {
        String root = System.getProperty("user.dir");

        try {
            AirFreshenerImage = ImageIO.read(new File(root + "/src/main/resources/items/airfreshener.png"));
            CombinedTransistorImage = ImageIO.read(new File(root + "/src/main/resources/items/transistor.png"));
            FFP2Image = ImageIO.read(new File(root + "/src/main/resources/items/ffp2.png"));
            HolyBeerImage = ImageIO.read(new File(root + "/src/main/resources/items/holybeer.png"));
            LogarlecImage = ImageIO.read(new File(root + "/src/main/resources/items/logarlec.png"));
            RottenCamembertImage = ImageIO.read(new File(root + "/src/main/resources/items/rotten_camembert.png"));
            TransistorImage = ImageIO.read(new File(root + "/src/main/resources/items/transistor.png"));
            TVSZImage = ImageIO.read(new File(root + "/src/main/resources/items/tvsz.png"));
            WetWipeImage = ImageIO.read(new File(root + "/src/main/resources/items/wetwipe.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void occupyRoomPosition(int x, int y) {
        int margin = (int)(RoomView.SIZE * 0.1);
        int contentLength = RoomView.SIZE - 2 * margin;
        int itemSpaceTotal = contentLength / 3;
        boolean heightIsGreater = itemImage.getHeight() > itemImage.getWidth();
        int yPosition = RoomView.SIZE - margin - itemSpaceTotal * (y + 1); // Adjust the y position

        if (heightIsGreater)
            this.setBounds(
                    margin + itemSpaceTotal * x,
                    yPosition,
                    UITool.fitWidth2AspectRatio(itemImage, itemSpaceTotal),
                    itemSpaceTotal
            );
        else this.setBounds(
                margin + itemSpaceTotal * x,
                yPosition,
                itemSpaceTotal,
                UITool.fitHeight2AspectRatio(itemImage, itemSpaceTotal)
        );}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        boolean heightIsGreater = itemImage.getHeight() > itemImage.getWidth();
        int margin = (int)(RoomView.SIZE * 0.1);
        int contentLength = RoomView.SIZE - 2 * margin;
        int itemSpaceTotal = contentLength / 3;
        if (heightIsGreater)
            g.drawImage(itemImage, 0, 0, UITool.fitWidth2AspectRatio(itemImage, itemSpaceTotal), itemSpaceTotal, null);
        else g.drawImage(itemImage, 0, 0, itemSpaceTotal, UITool.fitHeight2AspectRatio(itemImage, itemSpaceTotal), null);
    }

    @Override
    public void isInRoom(boolean isInRoom){
        int id = this.item.getRoom().getID();
        if (!isInRoom) { // if picked up
            GameMenu.gameWindowContainer[0].gamePanel.generator.rooms[id / 5][id % 5].removeItem(this);
            this.setVisible(false);
        }
        else { // if put down
            GameMenu.gameWindowContainer[0].gamePanel.generator.rooms[id / 5][id % 5].addItem(this);
            this.setVisible(true);
        }
        GameMenu.gameWindowContainer[0].gamePanel.generator.rooms[id / 5][id % 5].repaintCorrectly();
        repaint();
    }
}


