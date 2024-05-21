package com.redvas.app;

import com.redvas.app.map.Labyrinth;
import com.redvas.app.players.Player;
import com.redvas.app.ui.GameOverListener;
import com.redvas.app.ui.GeneratorListener;
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
import java.time.Duration;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
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

    protected static final Logger logger = App.getConsoleLogger(Game.class.getName());

    private final Set<Steppable> steppablesForRound = ConcurrentHashMap.newKeySet();

    public transient Labyrinth labyrinth;


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
            if (!end)
                s.step();

            if (end) return;
        }
    }

    private GameOverListener GOListener = null;
    public void setGOListener(GameOverListener listener) {
        this.GOListener = listener;
    }

    public void undergraduateVictory() {
        logger.fine("Undergraduate team won the game!");
        end = true;
        if (GOListener != null)
            GOListener.onGameOver(true);
        try {
            Thread.sleep(Duration.ofSeconds(5));
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
        System.exit(0);
    }

    private void professorVictory() {
        logger.fine("Professor team won the game!");
        end = true;
        if (GOListener != null)
            GOListener.onGameOver(false);
        try {
            Thread.sleep(Duration.ofSeconds(5));
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
        System.exit(0);
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

    private void commandStart() throws ParserConfigurationException, TransformerException {
        logger.fine("How many players?");
        int playerCount = App.reader.nextInt();
        labyrinth = new Labyrinth(width, height, playerCount, this);
        if(App.reader.hasNextLine()) App.reader.nextLine();
        play();
    }

    private void loadPreset(int arg) throws IOException, ParserConfigurationException, ClassNotFoundException, InvocationTargetException, SAXException, NoSuchMethodException, InstantiationException, IllegalAccessException, TransformerException {
        load("./test_saves/" + arg + ".xml");
        play();
    }

    private void menu() throws ParserConfigurationException, TransformerException, IOException, ClassNotFoundException, InvocationTargetException, SAXException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        boolean badInput;

        logger.fine("""
                
                Type 'start' to start a new game,
                'load #' to load a preset gamestate,
                'load save' to load a saved game  or
                'quit' to exit the game
                """);

        Scanner stdin = App.reader;

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

    private void commandQuit() {
        System.exit(0);
    }

    private int width, height, players;

    private void init(int width, int height, int players) {
        this.width = width;
        this.height = height;
        this.players = players;
    }

    public Game(int width, int height) throws ParserConfigurationException, IOException, ClassNotFoundException, TransformerException, InvocationTargetException, SAXException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        init(width, height, players);
        menu();
    }


    public Game(int width, int height, int players, GeneratorListener listener) {
        init(width, height, players);
        labyrinth = new Labyrinth(width, height, this, players, listener);
    }

    public Game(int preset) throws IOException, ParserConfigurationException, ClassNotFoundException, InvocationTargetException, TransformerException, SAXException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        loadPreset(preset);
        play();
    }


    private void commandLoad(String arg) throws ParserConfigurationException, TransformerException, IOException, ClassNotFoundException, InvocationTargetException, SAXException, NoSuchMethodException, InstantiationException, IllegalAccessException{
        try {
            int i = Integer.parseInt(arg);
            loadPreset(i);
        } catch (NumberFormatException ignored) {
            // ?
        }
    }

    // Amikkel a játékot vezérelni fogjuk - vagy mégsem, hiszen megoldottam keyboard listenerrel. Majd ha úgy döntünk hogy az jó akkor ezt ki lehet szedni

    public void movePlayer(Player p) {
        try {
            System.out.print("Enter a character to move the player (W/A/S/D): ");
            char character = (char) System.in.read();

            switch (character) {
                case 'W':
                case 'w':
                    //moveUp(p);
                    break;
                case 'A':
                case 'a':
                    //moveLeft(p);
                    break;
                case 'S':
                case 's':
                    //moveDown(p);
                    break;
                case 'D':
                case 'd':
                    //moveRight(p);
                    break;
                default:
                    System.out.println("Invalid input. Please enter W, A, S, or D.");
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
