package com.redvas.app;


import com.redvas.app.proto.Prototype;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App
{
    public static final Scanner reader = new Scanner(System.in);

    public static void main(String[] args) throws ParserConfigurationException, TransformerException {
        new Prototype();

        //probably graphic will be here
    }

    public static Logger getConsoleLogger(String className) {
        Logger logger = Logger.getLogger(className);
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.FINE);
        logger.addHandler(consoleHandler);
        logger.setLevel(Level.FINEST);
        return logger;
    }
}
