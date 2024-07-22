package com.batery.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ViewCenterConfig extends JPanel {
    public JSpinner spinnerBattery;
    private static ViewCenterConfig instance;
    private Box box;
    
    private ViewCenterConfig() {
        box = Box.createVerticalBox();
        setBackground(new Color(0, 0, 255, 13));
        startComponent();
        add(box);
    }

    public static ViewCenterConfig getInstance() {
        if (instance == null) {
            instance = new ViewCenterConfig();
        }
        return instance;
    }

    private void startComponent(){
        box.add(ComponentPrefab.labelDefault("Limit Battery", null, Color.BLUE, new Font("Bell MT", 1, 16)));

        spinnerBattery = new JSpinner();
        spinnerBattery.setModel(new SpinnerNumberModel(0, 0, 100, 1));
        spinnerBattery.setValue(ViewMain.LIMIT_BATTERY);
        box.add(spinnerBattery);

        box.add(ComponentPrefab.buttonDynamic("Save",
                new EmptyBorder(5,5,5,5),
                Color.GREEN,
                Color.WHITE,
                (event)->{
                    ViewMain.LIMIT_BATTERY=(int) spinnerBattery.getValue();
                }
        ));


        box.add(ComponentPrefab.labelDefault("Time Alert", null, Color.BLUE, new Font("Bell MT", 1, 16)));
        JSpinner spinnerTime = new JSpinner();
        spinnerTime.setModel(new SpinnerNumberModel(0, 0, 100, 1));
        spinnerTime.setValue(ViewMain.LIMIT_TIME);
        box.add(spinnerTime);

        box.add(ComponentPrefab.buttonDynamic("Save",
                new EmptyBorder(5,5,5,5),
                Color.GREEN,
                Color.WHITE,
                (event)->{
                    ViewMain.LIMIT_BATTERY=(int) spinnerBattery.getValue();
                }
        ));

        //Adding File Music
        box.add(ComponentPrefab.labelDefault("MUSIC", null, Color.BLUE, new Font("Bell MT", 1, 16)));
        box.add(ComponentPrefab.buttonDynamic("Select file",
                new EmptyBorder(2,5,2,5),
                Color.GREEN,
                Color.WHITE,
                (event)->{
                    openFileChooser();
                }
        ));
    }

    private void openFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a Music");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Music Files", "mp3", "wav", "m4a"));
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
