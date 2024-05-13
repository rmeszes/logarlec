package com.redvas.app.proto;

import com.redvas.app.App;
import com.redvas.app.Game;
import com.redvas.app.ui.GamePanel;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Prototype {
    private static final Logger logger = App.getConsoleLogger(Prototype.class.getName());
    public Prototype() throws ParserConfigurationException, TransformerException, IOException, ClassNotFoundException, InvocationTargetException, SAXException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        logger.fine("\nApp started successfully.");
        new GamePanel(6, 4, 2);
    }
}
