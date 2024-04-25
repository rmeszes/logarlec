package com.redvas.app.map;

import com.redvas.app.App;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Door {

    protected static final Logger logger = App.getConsoleLogger(Door.class.getName());
    public Door(Room connectsTo, boolean isPassable) {
        logger.finest("Door init");
    }

    /**
     *
     * @return Room: that is accessible through this door
     */
    public Room connectsTo(){
        logger.finest("The door connects to this room.");
        return new Room();
    }

    /**
     *
     * @param room: neighboring room
     */
    public void setConnection(Room room){
        logger.finest("This door now connects to a new Room");
    }

    /**
     *
     * @return bool
     */
    public boolean isPassable(){
        return true;
    }

    /**
     *
     * @param bool: true if door is open (?)
     */
    public void setPassable(boolean bool){
        if(bool /* && passable != bool */) {
            logger.finest("This door is now passable");
        } else {
            logger.finest("This door is no longer passable.");
        }
    }

    /**
     *
     * @return bool: whether the door is vanished
     */
    public boolean isVanished(){
        return false;
    }

    /**
     *
     * @param bool: true if the door is vanished
     */
    public void setVanished(boolean bool){
        if(bool /* && vanished != bool */) {
            logger.finest("This door has vanished");
        } else {
            logger.finest("The door has appeared");
        }
    }
}
