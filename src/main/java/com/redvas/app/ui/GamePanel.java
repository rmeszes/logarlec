package com.redvas.app.ui;

import com.redvas.app.App;
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
import java.util.Comparator;
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

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(width * RoomView.SIZE, height * RoomView.SIZE);
    }

    private int width, height;
    public GamePanel(int width, int height, int players) {
        this.width = width;
        this.height = height;
        JFrame window = new JFrame();
        window.add(this);
        // window.setSize(width * RoomView.SIZE,height * RoomView.SIZE);
        window.setVisible(true); //Don't show window for tests
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.pack();
        game = new Game();

        if (App.isTest())
            game.

        generator = new ViewGenerator(width, height, players, game);
        views = generator.getViews();
        views.sort(Comparator.comparingInt(a -> a.z));
        repaint();
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