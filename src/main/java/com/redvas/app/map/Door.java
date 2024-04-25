package com.redvas.app.map;


import com.redvas.app.App;

import java.util.logging.Logger;

public class Door {
    private Room connectsTo;
    private boolean passable;
    private boolean vanished;

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
        return this.connectsTo;
        return new Room();
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
        return true;

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
        return false;

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

    }
}
