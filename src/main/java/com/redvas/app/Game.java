package com.redvas.app;

import com.redvas.app.map.Labyrinth;
import com.redvas.app.ui.GamePanel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

public class Game extends JPanel{

    public void load(String path) throws IOException, SAXException, ParserConfigurationException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(path));
        Element game = document.getDocumentElement();
        labyrinth = Labyrinth.loadXML((Element) game.getElementsByTagName("labyrinth").item(0), this);
    }

    public void save() throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
        Element game = document.createElement("game");
        document.appendChild(game);
        Element labyrinthXML = this.labyrinth.saveXML(document);
        game.appendChild(labyrinthXML);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // Enable indentation
        transformer.setOutputProperty("{https://xml.apache.org/xslt}indent-amount", "2");
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File("autosave.xml"));
        transformer.transform(source, result);
    }

    private static final Random random = new Random();
    protected static final Logger logger = App.getConsoleLogger(Game.class.getName());

    private final Set<Steppable> steppablesForRound = new HashSet<>();

    private transient Labyrinth labyrinth;
    private GamePanel gamePanel;


    /**
     * method for when the game is started from scratch
     */

    /**
     * method for when the game has to load a previous save
     */

    /**
     * method for when the game has to load a preset
     */


    public void registerSteppable(Steppable steppable) {
        steppablesForRound.add(steppable);
        logger.finest(() -> String.format("Registering steppable: %s%n", steppable));
    }

    public void play() throws ParserConfigurationException, TransformerException {
        while (!end) {
            playRound();
            save();
        }
    }

    public void playRound() {
        logger.fine("New round");

        for (Steppable s : steppablesForRound) {
            s.step();

            if (end) return;
        }
    }

    public void undergraduateVictory() {
        logger.fine("Undergraduate team won the game!");
        end = true;
    }

    private void professorVictory() {
        logger.fine("Professor team won the game!");
        end = true;
    }

    private int undergraduates = 0;
    private boolean end = false;

    public void addUndergraduate() {
        undergraduates++;
    }

    public void undergraduateDroppedout() {
        if (--undergraduates == 0)
            professorVictory();
    }

    public void unRegisterSteppable(Steppable s) {
        steppablesForRound.remove(s);
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    private void menu() throws ParserConfigurationException, TransformerException, IOException, ClassNotFoundException, InvocationTargetException, SAXException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        boolean badInput;

        logger.fine("""
                
                Type 'start' to start a new game,
                'load #' to load a preset gamestate,
                'load save' to load a saved game  or
                'quit' to exit the game
                """);

        do {
            String input = stdin.nextLine();
            String[] inputSplit = input.split(" "); //need this so I can make the switch statement

            badInput = false;
            switch(inputSplit[0]) {
                case "start" -> commandStart();     // meghívja a game ctor-ját menü nélkül
                case "load" -> commandLoad(inputSplit[1]);
                case "quit" -> commandQuit();
                default -> {
                    badInput = true;
                    logger.warning("\nUnknown command");
                }
            }
        } while(badInput);
    }



    private void commandLoad(String arg) throws ParserConfigurationException, TransformerException, IOException, ClassNotFoundException, InvocationTargetException, SAXException, NoSuchMethodException, InstantiationException, IllegalAccessException{
        try {
            int i = Integer.parseInt(arg);
            game = Game.loadPreset(i);
        } catch (NumberFormatException ignored) {
            game = Game.loadGame(arg);
        }
    }
}
