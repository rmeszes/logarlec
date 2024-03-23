package com.redvas.app.map;

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

    public Room connectsTo(){
        logger.fine("The door connects to this room.");
        return new Room();
    }

    public void setConnection(Room room){
        logger.fine("Connection of rooms set to true due to a door's appearance.");
    }

    public boolean isPassable(){
        logger.fine("This door is active.");
        return true;
    }

    public void setPassable(boolean bool){
        logger.fine("This door is set to active.");
    }

    public boolean isVanished(){
        logger.fine("This door is not vanished.");
        return false;
    }

    public void setVanished(boolean bool){
        ;
    }
}
