package com.redvas.app.ui;

import com.redvas.app.Game;
import com.redvas.app.map.*;
import com.redvas.app.map.rooms.*;
import com.redvas.app.players.Player;
import com.redvas.app.ui.rooms.RoomView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel {
    public final transient ViewGenerator generator;
    public Player playerToMove;     // valahol kéne egy ilyet is tárolni, hogy tudja a keyboardListener hogy kit kell mozgatni, erre is kéne valami logika ami a köröket lebonyolítja
    // IDEAS below
    // valahogyan kéne tárolni az aktuális state-t (lépés, act), hogy a keyboardListener csakk a mozgásnál

    // called view Views, whenever a change occurs
    /*
    * public GamePanel(int preset) {}
    * */


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

        generator = new ViewGenerator(width, height, players, this);

        setFocusable(true);
        requestFocusInWindow();

        /*addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char keyChar = e.getKeyChar();
                switch (keyChar) {
                    case 'W':
                    case 'w':
                        System.out.println("-------------\ncurrent room id: " + playerToMove.where().getID());
                        if(playerToMove.moveTowards(Direction.UP)) {        // a moveTowards miért boolean? Ha sikeres akkor váltson a playerToMove egy másikra, külünben pedig ne, ennek a logikája TODO
                            System.out.println("sikeres W");
                            System.out.println("new room id: " + playerToMove.where().getID());
                        }
                        else {
                            System.out.println("sikertelen W");
                            Room r = playerToMove.where().isAccessible(Direction.UP);
                            if (r != null)
                                System.out.println("destination capacity: " + r.getCapacity() + " currently: " + r.getOccupants().size());
                        }
                        break;
                    case 'A':
                    case 'a':
                        System.out.println("-------------\ncurrent room id: " + playerToMove.where().getID());
                        if(playerToMove.moveTowards(Direction.LEFT)) {
                            System.out.println("sikeres A");
                            System.out.println("new room id: " + playerToMove.where().getID());
                            repaint();
                        }
                        else {
                            System.out.println("sikertelen A");
                            Room r = playerToMove.where().isAccessible(Direction.LEFT);
                            if (r != null)
                                System.out.println("destination capacity: " + r.getCapacity() + " currently: " + r.getOccupants().size());
                        }
                        break;
                    case 'S':
                    case 's':
                        System.out.println("-------------\ncurrent room id: " + playerToMove.where().getID());
                        if(playerToMove.moveTowards(Direction.DOWN)) {
                            System.out.println("sikeres S");
                            System.out.println("new room id: " + playerToMove.where().getID());
                            repaint();
                        }
                        else {
                            System.out.println("sikertelen S");
                            Room r = playerToMove.where().isAccessible(Direction.DOWN);
                            if (r != null)
                                System.out.println("destination capacity: " + r.getCapacity() + " currently: " + r.getOccupants().size());
                        }
                        break;
                    case 'D':
                    case 'd':
                        System.out.println("-------------\ncurrent room id: " + playerToMove.where().getID());
                        if(playerToMove.moveTowards(Direction.RIGHT)) {
                            System.out.println("sikeres D");
                            System.out.println("new room id: " + playerToMove.where().getID());
                            repaint();
                        }
                        else {
                            System.out.println("sikertelen D");
                            Room r = playerToMove.where().isAccessible(Direction.RIGHT);
                            if (r != null)
                                System.out.println("destination capacity: " + r.getCapacity() + " currently: " + r.getOccupants().size());
                        }
                        break;
                    default:
                        System.out.println("Invalid input. Please enter W, A, S, or D.");
                        break;
                }
                repaint();  // Repaint the panel to update any changes
            }
        });
         */

        repaint();
    }

    public static final double uiScale = 1.0; // FULL HD-n a 2.0 a jó

    public static double getMagnification() { return uiScale; }

}