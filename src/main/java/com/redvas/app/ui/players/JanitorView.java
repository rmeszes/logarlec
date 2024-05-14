package com.redvas.app.ui.players;

import com.redvas.app.App;
import com.redvas.app.map.rooms.Room;
import com.redvas.app.players.Janitor;
import com.redvas.app.ui.View;
import com.redvas.app.ui.players.listeners.PlayerChangeListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class JanitorView extends JPanel implements PlayerChangeListener, View{
    private Janitor janitor;
    private boolean isFainted = false;

    private static final Logger logger = App.getConsoleLogger(JanitorView.class.getName());
    private BufferedImage janitorImage = null;
    private BufferedImage faintedJanitorImage = null;
    public JanitorView(Janitor janitor) {
        this.janitor = janitor;
        try {
            janitorImage = ImageIO.read(new File("src/main/resources/jantior.png"));
            faintedJanitorImage = ImageIO.read(new File("src/main/resources/fainted_jantior.png"));
        } catch (
                IOException e) {
            logger.severe(e.getMessage());
        }
    }

    @Override
    public void positionChanged(Room from, Room to) {

    }

    @Override
    public void faintedChanged(boolean isFainted) {
        if(isFainted)
            this.isFainted = true;
        else
            this.isFainted = false;
    }

    public void draw() {
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(this.isFainted)
            g.drawImage(faintedJanitorImage, 0, 0, 100, 100, null);
        else
            g.drawImage(janitorImage, 0, 0, 100, 100, null);
    }

}
