package com.redvas.app.ui;

import com.redvas.app.Game;
import com.redvas.app.map.Labyrinth;
import com.redvas.app.ui.rooms.RoomView;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {
    private final transient ViewGenerator generator;

    private List<View> views = new ArrayList<>();

    // called view Views, whenever a change occurs
    public void refresh() {
        repaint();
    }

    /*
    * public GamePanel(int preset) {}
    * */

    private Game game;

    public GamePanel(int width, int height, int players) {
        JFrame window = new JFrame();
        window.add(this);
        window.setSize(width * RoomView.SIZE,height * RoomView.SIZE);
        game = new Game();
        generator = new ViewGenerator(width, height, players, this, game);
    }
    private double uiScale = 1.0;

    public double getMagnification() { return uiScale; }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Cast the Graphics object to Graphics2D
        Graphics2D g2d = (Graphics2D) g;

        for (View v : views)
            v.draw(g2d);

        // Call the draw method of the labyrinth
        // labyrinth.draw(g2d);
    }
}