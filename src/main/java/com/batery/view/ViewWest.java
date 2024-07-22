package com.batery.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ViewWest extends JPanel {
    private static ViewWest instance;
    private JButton btnConfig;

    private ViewWest() {
        setLayout(new GridLayout(10, 1));
        setOpaque(true);
        setBackground(new Color(240, 240, 255));
        startComponent();
    }
    public static ViewWest getInstance() {
        if (instance == null) {
            instance = new ViewWest();
        }
        return instance;
    }
    private void startComponent(){
        ViewCenter.SCROLL_PANE.setViewportView(ViewCenterHome.getInstance());

        EmptyBorder border=new EmptyBorder(0,15,0,15);
        //ImageIcon icon=new ImageIcon("src/main/resources/image/home.png");
        JToggleButton toggleButton1=ComponentPrefab.toggleButtonDynamic(
                "Home",
                new ImageIcon(getClass().getResource("/asset/image/home.png")),
                border, null, null, (actionEvent)->{
                    ViewCenter.SCROLL_PANE.setViewportView(ViewCenterHome.getInstance());
                });
        JToggleButton toggleButton2=ComponentPrefab.toggleButtonDynamic("Config",
                new ImageIcon(getClass().getResource("/asset/image/config.png")), border,
                null, null, (actionEvent)->{
                    ViewCenter.SCROLL_PANE.setViewportView(ViewCenterConfig.getInstance());
                });
//10.30.50.17
        ButtonGroup group=new ButtonGroup();
        group.add(toggleButton1);
        group.add(toggleButton2);
        add(toggleButton1);
        add(toggleButton2);
    }
}
