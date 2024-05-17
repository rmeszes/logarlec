package com.redvas.app.ui.players;

import com.redvas.app.players.Player;
import com.redvas.app.ui.UITool;
import com.redvas.app.ui.rooms.RoomView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class PlayerView extends JPanel  {
    protected BufferedImage myImage;
    protected static final BufferedImage undergraduateImage;
    protected static final BufferedImage professorImage;
    protected static final BufferedImage janitorImage;
    protected static final BufferedImage professorParalyzedImage;
    protected static final BufferedImage drunkundergradImage;
    protected static final BufferedImage faintedjanitorImage;

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

private Player player;

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
        } catch (IOException e) {
            throw new RuntimeException(e);
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
