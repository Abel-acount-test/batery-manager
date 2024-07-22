package com.batery.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ViewNorth extends JPanel{
    private JLabel lblTitle;
    private JButton btnClose;
    private static ViewNorth instance;

    private ViewNorth(){
        setLayout(new BorderLayout());
        setBackground(new Color(0, 125, 184));

        startComponent();
    }

    public static ViewNorth getInstance(){
        if (instance == null){
            instance = new ViewNorth();
        }
        return instance;
    }

    public void startComponent(){
        ImageIcon imageIcon=new ImageIcon("src/main/resources/image/battery.png");
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(32,32, Image.SCALE_SMOOTH));
        add(new JLabel(imageIcon), BorderLayout.WEST);

        lblTitle = ComponentPrefab.labelDefault("  Battery Manager", null, Color.WHITE, new Font("Cooper Black", 0, 16));
        add(lblTitle, BorderLayout.CENTER);

        btnClose = ComponentPrefab.buttonDynamic("X", null, Color.RED, Color.WHITE, (actionEvent)->{System.exit(0);});
        btnClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnClose.setBorder(new EmptyBorder(0, 10, 0, 10));add(btnClose, BorderLayout.EAST);
    }
}
