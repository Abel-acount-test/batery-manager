package com.batery.view;

import com.batery.controller.ActionButtonClose;

import javax.swing.*;
import java.awt.*;

public class ViewMain extends JFrame {
    private static ViewMain viewMain;
    private JButton btnClose;
    private JPanel panelCenter, panelFooter, panelHead, panelLeft, panelRight;
    public static int LIMIT_BATTERY = 85;//85% in battery
    public static int LIMIT_TIME = 5;//5 seconds

    private ViewMain() {
        super("Battery Manager 1.1");

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(350, 450);
        setLocationRelativeTo(null);
        //setLayout(new BoxLayout(this, 2)); // Cambiar a GridBagLayout
        setResizable(false);
        //setIconImage(new ImageIcon("src/main/java/com/batery/asset/image/icon.png").getImage());
        setIconImage(new ImageIcon(getClass().getResource( "/asset/image/icon.png")).getImage());

        //startComponent();

        panelHead = ViewNorth.getInstance();
        panelCenter = ViewCenter.getInstance();
        panelFooter = ComponentPrefab.createPanel(Color.GREEN, new FlowLayout(), null);
        panelLeft = ViewWest.getInstance();
        panelRight = ComponentPrefab.createPanel(Color.CYAN, new FlowLayout(), null);

        add(panelCenter, BorderLayout.CENTER);
        add(panelFooter, BorderLayout.SOUTH);
        add(panelHead, BorderLayout.NORTH);
        add(panelLeft, BorderLayout.WEST);
        add(panelRight, BorderLayout.EAST);
    }

    public static ViewMain getInstance() {
        if (viewMain == null) {
            viewMain = new ViewMain();
        }
        return viewMain;
    }


    public void viewFrame() {
        viewMain.setVisible(true);
    }

    public void closeFrame() {
        viewMain.setVisible(false);
    }

    private void startComponent() {
        btnClose = ComponentPrefab.buttonDefault("Close",
                null,
                Color.RED,
                Color.WHITE,
                new ActionButtonClose(btnClose),
                null);
        add(btnClose);

        // Salto de línea
        JLabel label = new JLabel("limit:");
        add(label);

        JTextField textField = new JTextField();
        textField.setColumns(10);
        textField.setText(String.valueOf(ViewMain.LIMIT_BATTERY));
        add(textField);

        JButton button = ComponentPrefab.buttonDefault("Save Limit",
                null,Color.BLUE,
                Color.WHITE,
                actionEvent -> {LIMIT_BATTERY = Integer.parseInt(textField.getText());},
                null);
        add(button);

        // Salto de línea
        JLabel label1 = new JLabel("time in seconds:");
        add(label1);

        JTextField textField1 = new JTextField();
        textField1.setColumns(10);
        textField1.setText(String.valueOf(ViewMain.LIMIT_TIME));
        add(textField1);

        JButton button1 = ComponentPrefab.buttonDefault("Save Time",
                null,
                Color.YELLOW,
                Color.BLUE,
                actionEvent -> {LIMIT_TIME = Integer.parseInt(textField1.getText());},
                null);

        add(button1);
    }

}
