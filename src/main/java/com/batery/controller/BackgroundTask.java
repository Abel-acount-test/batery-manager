package com.batery.controller;

import com.batery.utils.other.BatteryUtils;
import com.batery.view.ViewCenterConfig;
import com.batery.view.ViewCenterHome;
import com.batery.view.ViewMain;

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
                ViewCenterHome.getInstance().updateColor();
            } catch (Exception e) {
                //
                // JOptionPane.showMessageDialog(null, e.getMessage());
                e.printStackTrace();
                //System.exit(0);
            }
        }
    }


    private void actionBattery() throws Exception{
        batteryPercentage= BatteryUtils.getBatteryPercentage();
        if(batteryPercentage != -1 && batteryPercentage >= ViewMain.LIMIT_BATTERY){
            ViewMain.getInstance().viewFrame();
            //MusicController.getInstance().play(1);
            ViewMain.LIMIT_BATTERY=3+batteryPercentage;
            ViewCenterConfig.getInstance().spinnerBattery.setValue(ViewMain.LIMIT_BATTERY);
        }
        if(!BatteryUtils.isCharging()){
            //MusicController.getInstance().pause();
            ViewMain.getInstance().closeFrame();
        }
    }

}
