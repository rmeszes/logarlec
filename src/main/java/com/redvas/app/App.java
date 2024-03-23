package com.redvas.app;


import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App
{
    private static final Logger logger = Logger.getLogger("App");

    public static final Scanner reader = new Scanner(System.in);
    public static void main( String[] args )
    {
        ConsoleHandler handler = new ConsoleHandler();
        // PUBLISH this level
        handler.setLevel(Level.FINEST);
        logger.addHandler(handler);
        logger.setLevel(Level.FINEST);
        logger.log(Level.FINE, "App started");

        new Game();


        logger.log(Level.FINE, "App finish");
    }
}
