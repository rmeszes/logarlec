package com.redvas.app.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionPanel extends JPanel {

    private JLabel displayLabel;
    private JTextField inputField;

    public ActionPanel() {
        setLayout(new BorderLayout());

        displayLabel = new JLabel("Enter command:");
        add(displayLabel, BorderLayout.NORTH);

        inputField = new JTextField();
        add(inputField, BorderLayout.CENTER);

        // Listen for enter key press in the input field
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText();
                processInput(input);
                inputField.setText(""); // Clear the input field
            }
        });
    }

    private void processInput(String input) {
        System.out.println("User input: " + input);
        displayLabel.setText(input);
    }
}

