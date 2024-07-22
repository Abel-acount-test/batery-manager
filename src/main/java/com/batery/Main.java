package com.batery;

import com.batery.controller.BackgroundTask;
import com.batery.view.ViewMain;

public class Main {
    public static void main(String[] args) {
        BackgroundTask backgroundTask = BackgroundTask.getInstance();
        Thread backgroundThread = new Thread(backgroundTask);
        backgroundThread.start();

        ViewMain viewMain=ViewMain.getInstance();
        viewMain.viewFrame();
    }
}
