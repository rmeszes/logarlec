package com.redvas.app.ui.rooms;

import com.redvas.app.App;
import com.redvas.app.map.rooms.Room;
import com.redvas.app.players.Player;
import com.redvas.app.ui.PlayersView;
import com.redvas.app.ui.View;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RoomView extends View implements RoomChangeListener {
    private static final Logger logger = App.getConsoleLogger(RoomView.class.getName());

    private final Room room;
    private boolean isSticky = false;       // alapból legyen hamis, de am ez nem int a modellben??
    private boolean isGaseous = false;

    private BufferedImage floorImage;               // alap
    private BufferedImage floorImageWhenGaseous;    // amikor gázossá válik
    private BufferedImage floorImageWhenSticky;     // amikor ragadós a szoba
    private BufferedImage floorImageWhenGaseousAndSticky;     // amikor ragadós és gázos a szoba   VAN ILYEN??

    public RoomView(Room r, int x, int y) {
        z = 0;
        this.room = r;
        this.x = x;
        this.y = y;
        try {
            floorImage = ImageIO.read(new File("src/main/resources/floor.png"));
            floorImageWhenGaseous = ImageIO.read(new File("src/main/resources/floor.png"));
            floorImageWhenSticky = ImageIO.read(new File("src/main/resources/floor.png"));
            floorImageWhenGaseousAndSticky = ImageIO.read(new File("src/main/resources/floor.png"));
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }

    @Override
    public void roomStickinessChange(boolean isSticky) {    // Amikor a modellben változik, akkor ezt kell hívni és ez updateli a view-t
        this.isSticky = isSticky;

        // redraw majd itt ?    De ugye nem komponens, a tartalmazó cucc repaintj-je??
    }

    @Override
    public void roomGaseousnessChange(boolean isGaseous) {
        this.isGaseous = isGaseous;

    }

    public static BufferedImage flipY(BufferedImage image) {
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

    public static final int SIZE = 100;



    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);

        if (!isGaseous && !isSticky) {      // alap
            g.drawImage(floorImage, x * SIZE, y * SIZE, SIZE, SIZE, null);
        }

        else if (!isGaseous) {  // csak ragad
            g.drawImage(floorImageWhenSticky, x * SIZE, y * SIZE, SIZE, SIZE, null);
        }

        else if (!isSticky) {  // csak gázos
            g.drawImage(floorImageWhenGaseous, x * SIZE, y * SIZE, SIZE, SIZE, null);
        }

        else  {  // gázos és ragad
            g.drawImage(floorImageWhenGaseousAndSticky, x * SIZE, y * SIZE, SIZE, SIZE, null);
        }
    }
}
