package com.redvas.app;


import com.redvas.app.proto.Prototype;
import com.redvas.app.ui.GameMenu;
import com.redvas.app.ui.GamePanel;
import org.xml.sax.SAXException;

import javax.swing.*;
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
    private static boolean test = false;

    public static final Scanner reader = new Scanner(System.in);

    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException, ClassNotFoundException, InvocationTargetException, SAXException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if(args.length > 0 && args[0].equals("test")) {
            test = true;
        }

        if (test)
            Prototype.run();
        else {
            // menüből indítható, de a playerek/itemek a generálást követően eltűnnek
            SwingUtilities.invokeLater(() -> {
                GameMenu gameMenu = new GameMenu();
            });

            // nincs menü, itemek megjelennek
            //new GamePanel(5,5,4);
        }

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
