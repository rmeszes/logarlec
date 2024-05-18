package com.redvas.app.ui.rooms;

import com.redvas.app.App;
import com.redvas.app.map.Direction;
import com.redvas.app.map.Door;
import com.redvas.app.ui.UITool;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class DoorView extends JPanel implements DoorChangeListener {
    private static final Logger logger = App.getConsoleLogger(EnchantedRoomView.class.getName());
    private final Door door;
    private static BufferedImage leftDoorImage;
    private static BufferedImage rightDoorImage;
    private static BufferedImage topDoorImage;
    private static BufferedImage bottomDoorImage;
    private static BufferedImage verticalSymDoorImage;
    private static BufferedImage verticalVanishedDoorImage;
    private static BufferedImage horizontalVanishedDoorImage;
    private static BufferedImage horizontalSymDoorImage;
    private static BufferedImage verticalLockedDoor;
    private static BufferedImage horizontalLockedDoor;

    private static BufferedImage rotate90(BufferedImage image) {
        final double rads = Math.toRadians(90);
        final double sin = Math.abs(Math.sin(rads));
        final double cos = Math.abs(Math.cos(rads));
        final int w = (int) Math.floor(image.getWidth() * cos + image.getHeight() * sin);
        final int h = (int) Math.floor(image.getHeight() * cos + image.getWidth() * sin);
        final BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());
        final AffineTransform at = new AffineTransform();
        at.translate(w / 2d, h / 2d);
        at.rotate(rads,0, 0);
        at.translate(-image.getWidth() / 2d, -image.getHeight() / 2d);
        final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        rotateOp.filter(image,rotatedImage);
        return rotatedImage;
    }

    private static BufferedImage flipX(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage flippedImage = new BufferedImage(width, height, image.getType());

        Graphics2D g2d = flippedImage.createGraphics();
        AffineTransform at = AffineTransform.getScaleInstance(1, -1);
        at.translate(0, -height);
        g2d.transform(at);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return flippedImage;
    }

    public static final int VDOOR_WIDTH;

    static {
        try {
            String root = System.getProperty("user.dir");
            leftDoorImage = ImageIO.read(new File(root + "/src/main/resources/map/leftDoor.png"));
            verticalSymDoorImage = ImageIO.read(new File(root + "/src/main/resources/map/verticalSymDoor.png"));
            rightDoorImage = flipY(leftDoorImage);
            ImageIO.write(rightDoorImage, "PNG", new File("right.PNG"));
            topDoorImage = rotate90(leftDoorImage);
            bottomDoorImage = flipX(topDoorImage);
            horizontalSymDoorImage = rotate90(verticalSymDoorImage);
            verticalVanishedDoorImage = ImageIO.read(new File(root + "/src/main/resources/map/verticalVanishedDoor.png"));
            horizontalVanishedDoorImage = rotate90(verticalVanishedDoorImage);
            verticalLockedDoor = ImageIO.read(new File(root + "/src/main/resources/map/verticalLockedDoor.png"));
            horizontalLockedDoor = rotate90(verticalLockedDoor);
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }

        VDOOR_WIDTH = UITool.fitWidth2AspectRatio(verticalVanishedDoorImage, RoomView.SIZE);
    }


    private static BufferedImage flipY(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage flippedImage = new BufferedImage(width, height, image.getType());

        Graphics2D g2d = flippedImage.createGraphics();
        AffineTransform at = AffineTransform.getScaleInstance(-1, 1);
        at.translate(-width, 0);
        g2d.transform(at);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return flippedImage;
    }

    private int x, y, width, height;
    private BufferedImage myImage = null;
    public int width() { return width; }
    public int height() { return height;}
    public int globalX() { return x; }
    public int globalY() { return y; }
    private final Orientation orientation;
    private void update() {
        if (orientation == Orientation.Vertical) {
            if (door.isVanished()) {
                myImage = verticalVanishedDoorImage;
                width = UITool.fitWidth2AspectRatio(verticalVanishedDoorImage, RoomView.SIZE);
                height = RoomView.SIZE;
                x -= width / 2;
            }
            else if (door.isPassable(Direction.RIGHT) && door.isPassable(Direction.LEFT)) {
                myImage = verticalSymDoorImage;
                width = UITool.fitWidth2AspectRatio(verticalSymDoorImage, RoomView.SIZE);
                height = RoomView.SIZE;
                x -= width / 2;
            }
            else if (door.isPassable(Direction.RIGHT)) {
                myImage = rightDoorImage;
                width = UITool.fitWidth2AspectRatio(rightDoorImage, RoomView.SIZE);
                height = RoomView.SIZE;
                x -= VDOOR_WIDTH / 2;
            }
            else if (door.isPassable(Direction.LEFT)) {
                myImage = leftDoorImage;
                width = UITool.fitWidth2AspectRatio(leftDoorImage, RoomView.SIZE);
                height = RoomView.SIZE;
                x -= width;
                x += VDOOR_WIDTH / 2;
            }
            else {
                myImage = verticalLockedDoor;
                width = UITool.fitWidth2AspectRatio(verticalLockedDoor, RoomView.SIZE);
                height = RoomView.SIZE;
                x -= width / 2;
            }
        }
        else {
            if (door.isVanished()) {
                myImage = horizontalVanishedDoorImage;
                height = UITool.fitHeight2AspectRatio(horizontalVanishedDoorImage, RoomView.SIZE);
                width = RoomView.SIZE;
                y -= VDOOR_WIDTH / 2;
            }
            else if (door.isPassable(Direction.UP) && door.isPassable(Direction.DOWN)) {
                myImage = horizontalSymDoorImage;
                height = UITool.fitHeight2AspectRatio(horizontalSymDoorImage, RoomView.SIZE);
                width = RoomView.SIZE;
                y -= height / 2 - 1;
            }

            else if (door.isPassable(Direction.UP)) {
                myImage = topDoorImage;
                height = UITool.fitHeight2AspectRatio(topDoorImage, RoomView.SIZE);
                width = RoomView.SIZE;
                y -= height;
                y += VDOOR_WIDTH / 2;
            }
            else if (door.isPassable(Direction.DOWN)) {
                myImage = bottomDoorImage;
                height = UITool.fitHeight2AspectRatio(bottomDoorImage, RoomView.SIZE);
                width = RoomView.SIZE;
                y -= VDOOR_WIDTH / 2;
            } else {
                myImage = horizontalLockedDoor;
                height = UITool.fitHeight2AspectRatio(horizontalLockedDoor, RoomView.SIZE);
                width = RoomView.SIZE;
                y -= VDOOR_WIDTH / 2;
            }
        }

        setBounds(x, y, width, height);
        repaint();
    }

    public DoorView(Door door, int r1x, int r1y, int r2x, int r2y) {
        setOpaque(false);
        x = Math.max(r1x, r2x) * RoomView.SIZE;
        y = Math.max(r1y, r2y) * RoomView.SIZE;
        orientation = door.connectsTo(Direction.RIGHT) == null ? Orientation.Horizontal : Orientation.Vertical;
        this.door = door;
        update();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(myImage, 0, 0, width, height, null);
    }

    @Override
    public void changed() {
        update();
    }
}
