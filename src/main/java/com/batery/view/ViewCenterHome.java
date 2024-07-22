package com.batery.view;

import javax.swing.*;
import java.awt.*;

public class ViewCenterHome extends JPanel {
    private static ViewCenterHome instance;

    private ViewCenterHome() {
        setOpaque(true);
        setBackground(Color.RED);
    }
    public static ViewCenterHome getInstance() {
        if (instance == null) {
            instance = new ViewCenterHome();
        }
        return instance;
    }
}
