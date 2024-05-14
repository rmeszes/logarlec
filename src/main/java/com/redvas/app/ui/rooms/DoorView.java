package com.redvas.app.ui.rooms;

import com.redvas.app.App;
import com.redvas.app.map.Direction;
import com.redvas.app.map.Door;
import com.redvas.app.ui.UITool;
import com.redvas.app.ui.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.Utilities;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class DoorView extends JPanel {
    private static final Logger logger = App.getConsoleLogger(EnchantedRoomView.class.getName());
    private final Door door;
    private static BufferedImage
            leftDoorImage,
            rightDoorImage,
            topDoorImage,
            bottomDoorImage,
            verticalSymDoorImage,
            verticalVanishedDoorImage,
            horizontalVanishedDoorImage,
            horizontalSymDoorImage;

    private static BufferedImage rotate90(BufferedImage image) {
        /*int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage rotatedImage = new BufferedImage(height, width, image.getType());

        Graphics2D g2d = rotatedImage.createGraphics();
        AffineTransform at = AffineTransform.getTranslateInstance(height / 2, width / 2);
        at.rotate(Math.PI / 2);
        at.translate(-width / 2, -height / 2);
        g2d.drawImage(image, at, null);
        g2d.dispose();

        return rotatedImage;*/
        final double rads = Math.toRadians(90);
        final double sin = Math.abs(Math.sin(rads));
        final double cos = Math.abs(Math.cos(rads));
        final int w = (int) Math.floor(image.getWidth() * cos + image.getHeight() * sin);
        final int h = (int) Math.floor(image.getHeight() * cos + image.getWidth() * sin);
        final BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());
        final AffineTransform at = new AffineTransform();
        at.translate(w / 2, h / 2);
        at.rotate(rads,0, 0);
        at.translate(-image.getWidth() / 2, -image.getHeight() / 2);
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
    public DoorView(Door door) {
        setOpaque(false);

        if (door.connectsTo(Direction.RIGHT) != null) {
            // remember x and y specify top left coordinates
            orientation = Orientation.Vertical;
        }
        else {
            orientation = Orientation.Horizontal;
        }

        this.door = door;

    }

    private final Orientation orientation;



    @Override
    public void draw() {

    }
}
