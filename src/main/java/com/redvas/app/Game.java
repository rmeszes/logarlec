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
import java.awt.*;
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

    public Game(boolean isGraphicsMenu) throws ParserConfigurationException, TransformerException {
        int playerCount = 2; // legyen alapból 2
        if (!isGraphicsMenu) {
            logger.fine("How many players?");
            playerCount = App.reader.nextInt();
            if(App.reader.hasNextLine()) App.reader.nextLine();

            if(playerCount > 6) playerCount = 6;

            logger.fine("Starting new game..");
        }

        else {
            Integer[] playerOptions = {1, 2, 3, 4, 5, 6};           // ITT mik lehetnek? pl a 3-ra exception-t dob, de command line-ból is a protonal !!
            JComboBox<Integer> playerCountComboBox = new JComboBox<>(playerOptions);

            JPanel panel = new JPanel();
            panel.add(new JLabel("Number of players:"));
            panel.add(playerCountComboBox);

            int result = JOptionPane.showConfirmDialog(null, panel, "Set Game Parameters",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                playerCount = (int) playerCountComboBox.getSelectedItem(); // Get the selected item from the combo box
            } else {
                // User canceled, handle it as needed
                return;
            }
        }


        labyrinth = new Labyrinth(random.nextInt(4,12), random.nextInt(4,5), this,playerCount);
        createWindow();

        play();
    }

    private Game(String arg) throws ParserConfigurationException, IOException, ClassNotFoundException, InvocationTargetException, SAXException, NoSuchMethodException, InstantiationException, IllegalAccessException, TransformerException {
        logger.fine(() -> String.format("Loading game.. %s%n", arg));
        load(arg);

        createWindow();

        play();
    }

    private Game(int arg) throws IOException, ParserConfigurationException, ClassNotFoundException, InvocationTargetException, SAXException, NoSuchMethodException, InstantiationException, IllegalAccessException, TransformerException {
        logger.fine(() -> String.format("Loading preset: %d%n", arg));
        load("./test_saves/" + arg + ".xml");

        createWindow();

        play();
    }

    private void createWindow() {
        gamePanel = new GamePanel(labyrinth);
        JFrame window = new JFrame();
        window.add(gamePanel);
        window.setSize(600,800);
        if(Boolean.FALSE.equals(App.isTest())) window.setVisible(true); //Don't show window for tests
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * method for when the game is started from scratch
     */
    public static Game startNewGame(boolean withGraphicsMenu) throws ParserConfigurationException, TransformerException {
        return new Game(withGraphicsMenu);
    }

    /**
     * method for when the game has to load a previous save
     */
    public static Game loadGame(String arg) throws ParserConfigurationException, IOException, ClassNotFoundException, InvocationTargetException, SAXException, NoSuchMethodException, InstantiationException, IllegalAccessException, TransformerException {
        return new Game(arg);
    }

    /**
     * method for when the game has to load a preset
     */
    public static Game loadPreset(int arg) throws IOException, ParserConfigurationException, ClassNotFoundException, InvocationTargetException, SAXException, NoSuchMethodException, InstantiationException, IllegalAccessException, TransformerException {
        return new Game(arg);
    }

    public Set<Steppable> getSteppablesForRound() { return steppablesForRound; }

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

        for (Steppable s : getSteppablesForRound()) {
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

    public Labyrinth getLabyrinth() {
        return labyrinth;
    }

    public void setLabyrinth(Labyrinth labyrinth) {
        this.labyrinth = labyrinth;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}
