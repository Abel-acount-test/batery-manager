package com.batery.controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionButtonClose implements ActionListener {
    private JButton jButton;
    public ActionButtonClose(JButton jButton){
        this.jButton = jButton;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jButton) {
            System.exit(0);
        }
    }
}
