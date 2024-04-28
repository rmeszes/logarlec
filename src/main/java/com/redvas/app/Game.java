package com.redvas.app;

import com.redvas.app.map.Labyrinth;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

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

public class Game {
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
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File("last_save.xml"));
        transformer.transform(source, result);
    }

    private static final Random random = new Random();
    protected static final Logger logger = App.getConsoleLogger(Game.class.getName());

    private final Set<Steppable> steppablesForRound = new HashSet<>();

    private Labyrinth labyrinth;

    public Game() {
        logger.fine("Player1 Name: ");
        String player1Name = App.reader.nextLine();
        logger.fine(() -> String.format("Player1 name set to %s%n", player1Name));

        logger.fine("Player2 Name: ");
        String player2Name = App.reader.nextLine();
        logger.fine(() -> String.format("Player2 name set to %s%n", player2Name));

        logger.fine("Player names set.");

        labyrinth = new Labyrinth(random.nextInt(4,9), random.nextInt(4,9), this, player1Name, player2Name);
        play();
    }

    private Game(String arg) throws ParserConfigurationException, IOException, ClassNotFoundException, InvocationTargetException, SAXException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        logger.fine(() -> String.format("Loading game.. %s%n", arg));
        load(arg);
        play();
    }

    private Game(int arg) throws IOException, ParserConfigurationException, ClassNotFoundException, InvocationTargetException, SAXException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        logger.fine(() -> String.format("Loading preset: %d%n", arg));
        load("./test_saves/" + arg + ".xml");
        play();
    }

    /**
     * method for when the game is started from scratch
     */
    public static Game startNewGame() {
        return new Game();
    }

    /**
     * method for when the game has to load a previous save
     */
    public static Game loadGame(String arg) throws ParserConfigurationException, IOException, ClassNotFoundException, InvocationTargetException, SAXException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return new Game(arg);
    }

    /**
     * method for when the game has to load a preset
     */
    public static Game loadPreset(int arg) throws IOException, ParserConfigurationException, ClassNotFoundException, InvocationTargetException, SAXException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return new Game(arg);
    }

    private Set<Steppable> getSteppables() { return steppablesForRound; }

    public void registerSteppable(Steppable steppable) {
        steppablesForRound.add(steppable);
        logger.finest(() -> String.format("Registering steppable: %s%n", steppable));
    }

    public void play() {
        while (!end)
            playRound();
    }

    public void playRound() {
        logger.fine("New round");

        for (Steppable s : getSteppables()) {
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
}
