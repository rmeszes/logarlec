package com.redvas.app.ui.players;

import com.redvas.app.App;
import com.redvas.app.map.rooms.Room;
import com.redvas.app.players.Professor;
import com.redvas.app.ui.View;
import com.redvas.app.ui.players.listeners.PlayerChangeListener;
import com.redvas.app.ui.players.listeners.ProfessorChangeListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class ProfessorView extends JPanel implements PlayerChangeListener, ProfessorChangeListener, View {
    private final Professor professor;
    private static final Logger logger = App.getConsoleLogger(UndergraduateView.class.getName());

    private BufferedImage profImage;
    private BufferedImage paralyzedProfImage;
    private boolean isFainted;

    public ProfessorView(Professor professor) {
        this.professor = professor;
        try {
            profImage = ImageIO.read(new File("src/main/resources/prof.png"));
            paralyzedProfImage = ImageIO.read(new File("src/main/resources/paralyzed_prof.png"));
        } catch (
                IOException e) {
            logger.severe(e.getMessage());
        }
    }
    @Override
    public void positionChanged(Room from, Room to) {

    }



    @Override
    public void paralyzedChanged(boolean isParalyzed) {

    }
    @Override
    public void faintedChanged(boolean isFainted) {
        if(isFainted)
            this.isFainted = true;
        else
            this.isFainted = false;
    }

    @Override
    public void draw() {
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!this.isFainted)
            g.drawImage(profImage, 0, 0, 100, 100, null);
        else
            g.drawImage(paralyzedProfImage, 0, 0, 100, 100, null);
    }
}
