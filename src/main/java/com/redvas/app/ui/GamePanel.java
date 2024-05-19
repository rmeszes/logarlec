package com.redvas.app.ui;

import com.redvas.app.Game;
import com.redvas.app.map.Direction;
import com.redvas.app.players.Player;
import com.redvas.app.ui.rooms.RoomView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel {
    public final transient ViewGenerator generator;
    public Player playerToMove;     // valahol k�ne egy ilyet is t�rolni, hogy tudja a keyboardListener hogy kit kell mozgatni, erre is k�ne valami logika ami a k�r�ket lebonyol�tja
    // IDEAS below
    // valahogyan k�ne t�rolni az aktu�lis state-t (l�p�s, act), hogy a keyboardListener csakk a mozg�sn�l

    // called view Views, whenever a change occurs
    public void refresh() {
        repaint();
    }

    /*
    * public GamePanel(int preset) {}
    * */

    // public Game game;   // ez minek volt itt? xd  (a generator-ban van egy game)

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
        window.setVisible(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.pack();
         //   */

        generator = new ViewGenerator(width, height, players, this);

        setFocusable(true);
        requestFocusInWindow();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char keyChar = e.getKeyChar();
                switch (keyChar) {
                    case 'W':
                    case 'w':
                        if(playerToMove.moveTowards(Direction.UP)) {        // a moveTowards mi�rt boolean? Ha sikeres akkor v�ltson a playerToMove egy m�sikra, k�l�nben pedig ne, ennek a logik�ja TODO
                            System.out.println("sikeres W");
                        }
                        else {
                            System.out.println("sikertelen W");
                        }
                        break;
                    case 'A':
                    case 'a':
                        if(playerToMove.moveTowards(Direction.LEFT)) {
                            System.out.println("sikeres A");
                        }
                        else {
                            System.out.println("sikertelen A");
                        }
                        break;
                    case 'S':
                    case 's':
                        if(playerToMove.moveTowards(Direction.DOWN)) {
                            System.out.println("sikeres S");
                        }
                        else {
                            System.out.println("sikertelen S");
                        }
                        break;
                    case 'D':
                    case 'd':
                        if(playerToMove.moveTowards(Direction.RIGHT)) {
                            System.out.println("sikeres D");
                        }
                        else {
                            System.out.println("sikertelen D");
                        }
                        break;
                    default:
                        System.out.println("Invalid input. Please enter W, A, S, or D.");
                        break;
                }
                repaint();  // Repaint the panel to update any changes
            }
        });

        repaint();
    }
    public static final double uiScale = 3.0;

    public static double getMagnification() { return uiScale; }

}