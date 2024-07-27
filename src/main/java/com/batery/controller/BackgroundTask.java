package com.batery.controller;

import com.batery.view.ViewCenterConfig;
import com.batery.view.ViewMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BackgroundTask implements Runnable {
    private static BackgroundTask instance;
    private int batteryPercentage;
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
    private int getBatteryInfoUbuntu() {
        try {
            ProcessBuilder builder = new ProcessBuilder("bash", "-c", "upower -i $(upower -e | grep 'BAT') | grep 'percentage' | awk '{print $2}' | sed 's/%//'");
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

    private boolean isChargingWindows() {
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

    private boolean isChargingUbuntu() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", "upower -i $(upower -e | grep 'BAT') | grep 'state' | awk '{print $2}'");
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    return line.equalsIgnoreCase("charging");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void actionBattery() throws Exception{
        batteryPercentage=getBatteryInfoUbuntu();
        if(batteryPercentage != -1 && batteryPercentage >= ViewMain.LIMIT_BATTERY){
            ViewMain.getInstance().viewFrame();
            MusicController.getInstance().play(1);
            ViewMain.LIMIT_BATTERY=3+batteryPercentage;
            ViewCenterConfig.getInstance().spinnerBattery.setValue(ViewMain.LIMIT_BATTERY);
        }
        if(!isChargingUbuntu()){
            MusicController.getInstance().pause();
            ViewMain.getInstance().closeFrame();
        }
    }

}
