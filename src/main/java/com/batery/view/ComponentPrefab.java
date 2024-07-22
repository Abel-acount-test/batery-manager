package com.batery.view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class ComponentPrefab {
    public static JLabel labelDefault(String text, Color colorBackground, Color colorForeground, Font font){
        JLabel label = new JLabel();
        label.setText(text);
        label.setForeground(colorForeground);
        label.setBackground(colorBackground);
        label.setFont(font);
        label.setOpaque(true);
        return label;
    }

    public static JButton buttonDefault(String text, Border border, Color backgroundColor, Color foregroundColor, ActionListener actionListener, MouseListener mouseListener) {
        JButton button = new JButton(text);
        button.setBorder(border);
        button.setBackground(backgroundColor);
        button.setForeground(foregroundColor);
        button.setOpaque(true);
        button.addActionListener(actionListener);
        button.addMouseListener(mouseListener);
        return button;
    }

    public static JButton buttonDynamic(String text, Border border, Color backgroundColor, Color foregroundColor, ActionListener actionListener) {
        JButton button = buttonDefault(text, border, backgroundColor, foregroundColor, actionListener, null);
        button.setFocusPainted(false);
        button.addMouseListener(
                new MouseAdapter() {
                    @Override public void mouseClicked(MouseEvent e) {
                        Color color = button.getBackground();
                        button.setBackground(normalizeColor(color.getRed()+10, color.getGreen()+10, color.getBlue()+10));
                    }
                    @Override public void mousePressed(MouseEvent e) {}
                    @Override public void mouseReleased(MouseEvent e) {
                        Color color = button.getBackground();
                        button.setBackground(normalizeColor(color.getRed()-10, color.getGreen()-10, color.getBlue()-10));
                    }
                    @Override public void mouseEntered(MouseEvent e) {
                        Color color = button.getBackground();
                        button.setBackground(normalizeColor(color.getRed()-30, color.getGreen()-30, color.getBlue()-30));
                    }
                    @Override public void mouseExited(MouseEvent e) {
                        Color color = button.getBackground();
                        button.setBackground(normalizeColor(color.getRed()+30, color.getGreen()+30, color.getBlue()+30));
                    }
                }
        );
        return button;
    }

    public static JToggleButton toggleButtonDefault(String text, ImageIcon icon, Border border, Color backgroundColor, Color foregroundColor, ActionListener actionListener, MouseListener mouseListener){
        if(icon!=null){
            icon.setImage(icon.getImage().getScaledInstance(16,16, Image.SCALE_SMOOTH));
        }
        JToggleButton toggleButton = new JToggleButton(text, icon);

        toggleButton.setBorder(border);
        toggleButton.setBackground(backgroundColor);
        toggleButton.setForeground(foregroundColor);
        toggleButton.setOpaque(true);
        toggleButton.addActionListener(actionListener);
        toggleButton.addMouseListener(mouseListener);
        return toggleButton;
    }

    public static JToggleButton toggleButtonDynamic(String text, ImageIcon icon, Border border, Color backgroundColor, Color foregroundColor, ActionListener actionListener) {
        JToggleButton jToggleButton = toggleButtonDefault(text, icon, border, backgroundColor, foregroundColor, actionListener, null);
        jToggleButton.setFocusPainted(false);
        jToggleButton.addMouseListener(
                new MouseAdapter() {
                    @Override public void mouseClicked(MouseEvent e) {}
                    @Override public void mousePressed(MouseEvent e) {}
                    @Override public void mouseReleased(MouseEvent e) {}
                    @Override public void mouseEntered(MouseEvent e) {
                        Color color = jToggleButton.getBackground();
                        jToggleButton.setBackground(normalizeColor(color.getRed()-30, color.getGreen()-30, color.getBlue()-30));
                    }
                    @Override public void mouseExited(MouseEvent e) {
                        Color color = jToggleButton.getBackground();
                        jToggleButton.setBackground(normalizeColor(color.getRed()+30, color.getGreen()+30, color.getBlue()+30));
                    }

                }
        );
        jToggleButton.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                jToggleButton.setForeground(Color.BLUE);
            }
            @Override public void focusLost(FocusEvent e) {
                jToggleButton.setForeground(foregroundColor);
            }
        });
        return jToggleButton;
    }

    public static JPanel createPanel(Color color, LayoutManager layout, Border border) {
        JPanel panel = new JPanel();
        panel.setLayout(layout);
        panel.setBackground(color);
        panel.setBorder(border);
        return panel;
    }


    private static Color normalizeColor(int r, int g, int b) {
        if(r>255) r = 255;
        if(g>255) g = 255;
        if(b>255) b = 255;
        if(r<0) r = 0;
        if(g<0) g = 0;
        if(b<0) b = 0;
        return new Color(r, g, b);
    }
}
