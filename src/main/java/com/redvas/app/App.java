package com.redvas.app;


import com.redvas.app.map.Labyrinth;

import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App
{
    private static final Logger logger = Logger.getLogger("App");

    public static final Scanner reader = new Scanner(System.in);
    public App()
    {
        ConsoleHandler handler = new ConsoleHandler();
        // PUBLISH this level
        handler.setLevel(Level.FINEST);
        logger.addHandler(handler);
        logger.setLevel(Level.FINEST);
        logger.log(Level.FINE, "App started");

        Labyrinth labyrinth = new Labyrinth();

        logger.log(Level.FINE, "App finish");
    }
}
