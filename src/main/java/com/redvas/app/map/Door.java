package com.redvas.app.map;

import com.redvas.app.App;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Door {

    private Room connectsTo;
    private boolean passable;
    private boolean vanished;

    protected static final Logger logger = Logger.getLogger("Door");

    static {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINEST);
        logger.addHandler(handler);
        logger.setLevel(Level.FINEST);
    }
    public Door(Room connectsTo, boolean isPassable) {
        this.connectsTo = connectsTo;
        this.passable = isPassable;
        logger.finest("Door init");
    }

    /**
     *
     * @return Room: that is accessible through this door
     */
    public Room connectsTo(){
        logger.finest("The door connects to this room.");
        return this.connectsTo;
    }

    /**
     *
     * @param room: neighboring room
     */
    public void setConnection(Room room){
        this.connectsTo = room;
        logger.finest("This door now connects to a new Room");
    }

    /**
     *
     * @return bool
     */
    public boolean isPassable(){
        if(this.passable) {
            logger.finest("This door is active.");
            return true;
        }
        else{
            logger.finest("This door is inactive.");
            return false;
        }
    }

    /**
     *
     * @param bool: true if door is open
     */
    public void setPassable(boolean bool){
        if(bool) {
            this.passable = true;
            logger.finest("This door is now passable");
        } else {
            this.passable = false;
            logger.finest("This door is no longer passable.");
        }
    }

    /**
     *
     * @return bool: whether the door is vanished
     */
    public boolean isVanished(){
        if(this.vanished) {
            logger.finest("This door has vanished.");
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
        if(bool) {
            this.vanished = true;
            logger.finest("This door has vanished");
        } else {
            this.vanished = false;
            logger.finest("The door has appeared");
        }
    }
}
