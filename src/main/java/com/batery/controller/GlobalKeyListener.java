package com.batery.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GlobalKeyListener extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_E){
            System.out.println("E");
        }
    }
}
