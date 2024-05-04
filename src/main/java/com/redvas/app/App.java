package com.redvas.app;


import com.redvas.app.proto.Prototype;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App
{
    private static boolean test;
    private static boolean isGraphicsMenu;

    public static final Scanner reader = new Scanner(System.in);

    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException, ClassNotFoundException, InvocationTargetException, SAXException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if(args.length > 0 && args[0].equals("test")) {     // nem grafikus, tesztelésre
            test = true;
        }

        if(args.length > 0 && args[0].equals("graphics")) {     // grafikus, NEM tesztelésre
            isGraphicsMenu = true;
        }
        else {      // Ha nem grafikus menü
            isGraphicsMenu = false;
        }

        new Prototype(isGraphicsMenu);

        //probably graphic will be here
    }

    public static boolean isTest() {
        return test;
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
