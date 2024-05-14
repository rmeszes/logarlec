package com.redvas.app.ui.rooms;

import com.redvas.app.App;
import com.redvas.app.map.Direction;
import com.redvas.app.map.Door;
import com.redvas.app.ui.UITool;
import com.redvas.app.ui.View;

import javax.imageio.ImageIO;
import javax.swing.text.Utilities;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

enum Orientation {
    Horizontal,
    Vertical
}

public class DoorView extends View {
    private static final Logger logger = App.getConsoleLogger(EnchantedRoomView.class.getName());
    private final Door door;
    BufferedImage
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
    public DoorView(Door door, int r1x, int r1y, int r2x, int r2y) {
        z = 1;

        if (door.connectsTo(Direction.RIGHT) != null) {
            // remember x and y specify top left coordinates
            orientation = Orientation.Vertical;
            x = Math.max(r1x, r2x) * RoomView.SIZE;
            y = r1y * RoomView.SIZE;
        }
        else {
            orientation = Orientation.Horizontal;
            y = (Math.max(r1y, r2y)) * RoomView.SIZE;
            x = r1x * RoomView.SIZE;
        }

        this.door = door;
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
    }

    private final Orientation orientation;

    public void draw(Graphics2D g) {
        int s = UITool.fitWidth2AspectRatio(verticalVanishedDoorImage, RoomView.SIZE);

        if (orientation == Orientation.Vertical) {
            if (door.isVanished()) {
                int w = UITool.fitWidth2AspectRatio(verticalVanishedDoorImage, RoomView.SIZE);

                g.drawImage(verticalVanishedDoorImage,
                        x - s / 2, y,
                        w,
                        RoomView.SIZE,
                        null
                );
            }

            else if (door.isPassable(Direction.RIGHT) && door.isPassable(Direction.LEFT)) {
                int w = UITool.fitWidth2AspectRatio(verticalSymDoorImage, RoomView.SIZE);

                g.drawImage(verticalSymDoorImage,
                        x - w / 2, y,
                        w,
                        RoomView.SIZE,
                        null
                );
            }
            else if (door.isPassable(Direction.RIGHT)) {
                int w = UITool.fitWidth2AspectRatio(rightDoorImage, RoomView.SIZE);

                g.drawImage(rightDoorImage,
                        x - s / 2, y,
                        w,
                        RoomView.SIZE,
                        null
                );
            }

            else {
                int w = UITool.fitWidth2AspectRatio(leftDoorImage, RoomView.SIZE);

                g.drawImage(leftDoorImage,
                        x - w + s / 2, y,
                        w,
                        RoomView.SIZE,
                        null
                );
            }

        }
        else {
            if (door.isVanished()) {
                int h = UITool.fitHeight2AspectRatio(horizontalVanishedDoorImage, RoomView.SIZE);

                g.drawImage(horizontalVanishedDoorImage,
                        x,y - s / 2,
                        RoomView.SIZE,
                        h,
                        null
                );
            }
            else if (door.isPassable(Direction.UP) && door.isPassable(Direction.DOWN)) {
                int h = UITool.fitHeight2AspectRatio(horizontalSymDoorImage, RoomView.SIZE);

                g.drawImage(horizontalSymDoorImage,
                        x,y - h / 2,
                        RoomView.SIZE,
                        h,
                        null
                );
            }

            else if (door.isPassable(Direction.UP)) {
                int h = UITool.fitHeight2AspectRatio(topDoorImage, RoomView.SIZE);

                g.drawImage(topDoorImage,
                        x,y - h,
                        RoomView.SIZE,
                        h,
                        null
                );
            }
            else {
                int h = UITool.fitHeight2AspectRatio(bottomDoorImage, RoomView.SIZE);

                g.drawImage(bottomDoorImage,
                        x,y - s / 2,
                        RoomView.SIZE,
                        h,
                        null
                );
            }

        }
    }
}
