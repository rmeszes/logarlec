package com.redvas.app.ui;

import com.redvas.app.Game;
import com.redvas.app.ui.rooms.RoomView;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private final transient ViewGenerator generator;

    // called view Views, whenever a change occurs
    /*
    * public GamePanel(int preset) {}
    * */

    private Game game;

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(width * RoomView.SIZE, height * RoomView.SIZE);
    }

    private final int width;
    private final int height;
    public GamePanel(int width, int height, int players) {
        this.width = width;
        this.height = height;

        //   /*
        JFrame window = new JFrame();
        window.add(this);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        //   */

        generator = new ViewGenerator(width, height, players, this);
        window.pack();
        window.setVisible(true);
        window.repaint();
    }
    public static final double uiScale = 2.0;

    public static double getMagnification() { return uiScale; }


}