package com.redvas.app.ui.rooms;

import com.redvas.app.App;
import com.redvas.app.map.rooms.Room;
import com.redvas.app.players.Player;
import com.redvas.app.ui.GamePanel;
import com.redvas.app.ui.PlayersView;
import com.redvas.app.ui.View;
import com.redvas.app.ui.players.PlayerView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RoomView extends JPanel implements RoomChangeListener, View {
    private static final Logger logger = App.getConsoleLogger(RoomView.class.getName());
    private final Room room;
    private boolean isSticky = false;       // alapból legyen hamis, de am ez nem int a modellben??
    private boolean isGaseous = false;

    private BufferedImage floorImage;               // alap
    private BufferedImage floorImageWhenGaseous;    // amikor gázossá válik
    private BufferedImage floorImageWhenSticky;     // amikor ragadós a szoba
    private BufferedImage floorImageWhenGaseousAndSticky;     // amikor ragadós és gázos a szoba   VAN ILYEN??
    public RoomView(Room r, int x, int y) {
        this.room = r;
        setBounds(x * SIZE, y * SIZE, SIZE, SIZE);

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

    public static final int SIZE = (int)(100 * GamePanel.getMagnification());

    private List<PlayerView> playerViews = new ArrayList<>();

    public void draw() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);

        if (!isGaseous && !isSticky) {      // alap
            g.drawImage(floorImage, 0, 0, SIZE, SIZE, null);
        }

        else if (!isGaseous) {  // csak ragad
            g.drawImage(floorImageWhenSticky, 0, 0, SIZE, SIZE, null);
        }

        else if (!isSticky) {  // csak gázos
            g.drawImage(floorImageWhenGaseous, 0, 0, SIZE, SIZE, null);
        }

        else  {  // gázos és ragad
            g.drawImage(floorImageWhenGaseousAndSticky, 0, 0, SIZE, SIZE, null);
        }
    }
}
