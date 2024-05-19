package com.redvas.app;


import com.redvas.app.map.rooms.Room;
import com.redvas.app.players.Player;
import com.redvas.app.players.Undergraduate;
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
                 //GameMenu gameMenu = new GameMenu();
            });

            // nincs menü, itemek megjelennek
            GamePanel gp = new GamePanel(5,5,4);

            // vezérlés próbálkozások
            Game testgame = gp.generator.getGame();    // ezeket mind publicra tettem
            Room testRoom = testgame.labyrinth.rooms.get(1);
            Undergraduate testPlayer = new Undergraduate(1, testRoom, testgame);
            gp.playerToMove = testPlayer;

            // 1. verzió - konzolról adjuk meg az irányokat - NEM ÍGY KÉNE
            //testgame.movePlayer(testPlayer);


            // 2. verzió - implementáltam KeyListenert a GamePanelen belül, csak itt a körök lebonyolítását
            // kéne valahogyan megoldani. (összes lehetséges playeren végigmenni vmilyen sorrendben és ezekere rendre: lépés + act (ami opcionális) )    Plusz a szobák mikor is változnak?
            // Ezek a körök annyira nem tiszták nekem, de lehetne mondjuk úgy hogy ha minden játékos lépett egy adott körben
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
