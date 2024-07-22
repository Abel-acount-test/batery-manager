package com.batery.view;

import javax.swing.*;
import java.awt.*;

public class ViewCenter extends JPanel {
    private static ViewCenter instance;
    public static JScrollPane SCROLL_PANE;

    private ViewCenter() {
        setLayout(new BorderLayout());

        SCROLL_PANE = new JScrollPane();
        add(SCROLL_PANE, BorderLayout.CENTER);
    }

    public static ViewCenter getInstance() {
        if (instance == null) {
            instance=new ViewCenter();
        }
        return instance;
    }
}
