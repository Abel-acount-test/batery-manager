package com.batery.controller;

import com.batery.view.ViewCenterConfig;
import com.batery.view.ViewMain;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BackgroundTask implements Runnable {
    private static BackgroundTask instance;
    private BackgroundTask() {}
    public static BackgroundTask getInstance() {
        if (instance == null) {
            instance = new BackgroundTask();
        }
        return instance;
    }
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            try {
                actionBattery();
                Thread.sleep(ViewMain.LIMIT_TIME*1000);
            } catch (Exception e) {
                //
                // JOptionPane.showMessageDialog(null, e.getMessage());
                e.printStackTrace();
                //System.exit(0);
            }
        }
    }

    private int getBatteryInfoWindows() {
        try {
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "WMIC Path Win32_Battery Get EstimatedChargeRemaining");
            builder.redirectErrorStream(true);
            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    return Integer.parseInt(line.trim());
                } catch (NumberFormatException e) {
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
    private boolean isCharging() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "wmic path Win32_Battery get BatteryStatus");
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("BatteryStatus")) {
                    continue;
                }
                line = line.trim();
                if (!line.isEmpty()) {
                    int status = Integer.parseInt(line);
                    return status == 2; // 2 indicates charging
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    private void actionBattery() throws Exception{
        if(getBatteryInfoWindows() != -1 && getBatteryInfoWindows() >= ViewMain.LIMIT_BATTERY){
            ViewMain.getInstance().viewFrame();
            MusicController.getInstance().play(1);
            ViewMain.LIMIT_BATTERY=3+getBatteryInfoWindows();
            ViewCenterConfig.getInstance().spinnerBattery.setValue(ViewMain.LIMIT_BATTERY);
        }
        if(!isCharging()){
            MusicController.getInstance().pause();
            ViewMain.getInstance().closeFrame();
        }
    }

}
