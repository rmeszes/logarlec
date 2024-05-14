package com.redvas.app.ui.players;

import com.redvas.app.App;
import com.redvas.app.map.rooms.Room;
import com.redvas.app.players.Undergraduate;
import com.redvas.app.ui.players.listeners.PlayerChangeListener;
import com.redvas.app.ui.players.listeners.UndergraduateChangeListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class UndergraduateView extends JPanel implements UndergraduateChangeListener, PlayerChangeListener{
    private final Undergraduate undergraduate;
    private static final Logger logger = App.getConsoleLogger(UndergraduateView.class.getName());
    private BufferedImage undergradImage;
    private BufferedImage drunkUndergradImage;
    private boolean isFainted;
    public UndergraduateView(Undergraduate undergraduate) {

        this.undergraduate = undergraduate;
        try {
            undergradImage = ImageIO.read(new File("src/main/resources/undergrad.png"));
            drunkUndergradImage = ImageIO.read(new File("src/main/resources/drunk_undergrad.png"));
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
        if(!this.isFainted)
            g.drawImage(undergradImage, 0, 0, 100, 100, null);
        else
            g.drawImage(drunkUndergradImage, 0, 0, 100, 100, null);
    }

    @Override
    public void droppedOut() {

    }

}
