package com.batery.view;

import com.batery.utils.other.BatteryUtils;

import javax.swing.*;

public class ViewCenterHome extends JPanel {
    private static ViewCenterHome instance;

    private ViewCenterHome() {
        setOpaque(true);
        setBackground(BatteryUtils.colorByBattery());
    }

    public void updateColor(){
        setBackground(BatteryUtils.colorByBattery());
    }
    public static ViewCenterHome getInstance() {
        if (instance == null) {
            instance = new ViewCenterHome();
        }
        return instance;
    }
}
