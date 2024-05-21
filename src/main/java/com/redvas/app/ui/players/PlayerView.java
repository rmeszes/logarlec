package com.redvas.app.ui.players;

import com.redvas.app.App;
import com.redvas.app.players.Player;
import com.redvas.app.ui.UITool;
import com.redvas.app.ui.rooms.RoomView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public abstract class PlayerView extends JPanel  {
    private static final Logger logger = App.getConsoleLogger(Player.class.getName());

    protected transient BufferedImage myImage;
    protected static BufferedImage undergraduateImage;
    protected static BufferedImage professorImage;
    protected static BufferedImage janitorImage;
    protected static BufferedImage professorParalyzedImage;
    protected static BufferedImage drunkundergradImage;
    protected static BufferedImage faintedjanitorImage;
    protected static BufferedImage activeUndergraduateImage;

    protected boolean heightIsGreater = true;
    protected int playerSpaceTotal;

    public void occupyRoomPosition(int x, int y) {
        int margin = (int)(RoomView.SIZE * 0.1);
        int contentLength = RoomView.SIZE - 2 * margin;
        playerSpaceTotal = contentLength / 3;
        heightIsGreater = myImage.getHeight() > myImage.getWidth();

        if (heightIsGreater)
            this.setBounds(
                margin + playerSpaceTotal * x,
                margin + playerSpaceTotal * y,
                UITool.fitWidth2AspectRatio(myImage, playerSpaceTotal),
                    playerSpaceTotal
                );
        else this.setBounds(
                margin + playerSpaceTotal * x,
                margin + playerSpaceTotal * y,
                playerSpaceTotal,
                UITool.fitHeight2AspectRatio(myImage, playerSpaceTotal)
        );
    }

private transient Player player;

    public Player getPlayer() { return player; }

    protected PlayerView(Player player) {
        setOpaque(false);
        this.player = player;
    }

    static {
        String root = System.getProperty("user.dir");

        try {
            undergraduateImage = ImageIO.read(new File(root + "/src/main/resources/players/undergraduate.png"));
            professorImage = ImageIO.read(new File(root + "/src/main/resources/players/professor.png"));
            janitorImage = ImageIO.read(new File(root + "/src/main/resources/players/janitor.png"));
            faintedjanitorImage = ImageIO.read(new File(root + "/src/main/resources/players/fainted_janitor.png"));
            professorParalyzedImage = ImageIO.read(new File(root + "/src/main/resources/players/professor_paralyzed.png"));
            drunkundergradImage = ImageIO.read(new File(root + "/src/main/resources/players/drunk_undergrad.png"));
            activeUndergraduateImage = ImageIO.read(new File(root + "/src/main/resources/players/active_undergrad.png"));
        } catch (IOException e) {
            logger.severe(()->"Images could not be read: " + e.getMessage());
            System.exit(2);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (heightIsGreater)
            g.drawImage(myImage, 0, 0, UITool.fitWidth2AspectRatio(myImage, playerSpaceTotal), playerSpaceTotal, null);
        else g.drawImage(myImage, 0, 0, playerSpaceTotal, UITool.fitHeight2AspectRatio(myImage, playerSpaceTotal), null);
    }
}
