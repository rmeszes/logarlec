package com.redvas.app.map;

import com.redvas.app.App;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Door {

    protected static final Logger logger = Logger.getLogger("Door");

    static {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINEST);
        logger.addHandler(handler);
        logger.setLevel(Level.FINEST);
    }
    public Door() {
        logger.fine("Door init");
    }

    /**
     *
     * @return Room: that is accessible through this door
     */
    public Room connectsTo(){
        logger.fine("The door connects to this room.");
        return new Room();
    }

    /**
     *
     * @param room: neighboring room
     */
    public void setConnection(Room room){
        System.out.print("Are these rooms connected? (y/n)");
        String value = App.reader.nextLine();
        if(value.equals("y"))
            logger.fine("Connection of rooms set to true (eg. due to a door's appearance).");

        else
            logger.fine("Connection of rooms set to false (eg. due to a door's disappearance).");
    }

    /**
     *
     * @return bool
     */
    public boolean isPassable(){
        System.out.print("Is this door passable? (y/n)");
        String value = App.reader.nextLine();

        if(value.equals("y")) {
            logger.fine("This door is active.");
            return true;
        }
        else{
            logger.fine("This door is inactive.");
            return false;
        }
    }

    /**
     *
     * @param bool: true if door is open (?)
     */
    public void setPassable(boolean bool){
        logger.fine("This door is set to active.");
    }

    /**
     *
     * @return bool
     */
    public boolean isVanished(){
        System.out.print("Is this door vanished? (y/n)");
        String value = App.reader.nextLine();

        if(value.equals("y")) {
            logger.fine("This door has vanished.");
            return true;
        }
        else{
            logger.fine("This door hasn't vanished.");
            return false;
        }
    }

    /**
     *
     * @param bool: true if the door is vanished
     */
    public void setVanished(boolean bool){
        logger.fine("This door is set to vanished.");
    }
}
