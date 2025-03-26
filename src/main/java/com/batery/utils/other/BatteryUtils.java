package com.batery.utils.other;

import com.batery.controller.BackgroundTask;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class BatteryUtils {
    private BatteryUtils() {}

    public static Color colorByBattery(){

        int percentage = Math.max(0, Math.min(100, getBatteryPercentage()));

        int red, green;

        if (percentage <= 50) {
            red = 200;
            green = (int) (percentage * 5.1);
        } else {
            red = (int) ((100 - percentage) * 5.1);
            green = 200;
        }

        return new Color(red, green, 0);
    }

    public  static boolean isCharging(){
        try {
            //ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", "upower -i $(upower -e | grep 'BAT') | grep 'state' | awk '{print $2}'");
            ProcessBuilder processBuilder=getProcessBuilderChanging();
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;


            return switch (getOperatingSystem()){
                case WINDOWS -> {
                    while ((line = reader.readLine()) != null) {
                        if (line.contains("BatteryStatus")) {
                            continue;
                        }
                        line = line.trim();
                        if (!line.isEmpty()) {
                            int status = Integer.parseInt(line);
                            yield status == 2;
                        }
                    }
                    yield false;
                }
                case LINUX -> {
                    while ((line = reader.readLine()) != null) {
                        line = line.trim();
                        if (!line.isEmpty()) {
                            yield  line.equalsIgnoreCase("charging");
                        }
                    }
                    yield  false;
                }
                default -> false;
            };

        }catch (Exception e){
            //throw new RuntimeException(e.getMessage().concat("(error in isCharging)"));
            return false;
        }
    }

    public static int getBatteryPercentage() {

        try {
            ProcessBuilder builder = getProcessBuilderBatteryPercentage();
            builder.redirectErrorStream(true);
            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    return Integer.parseInt(line.trim());
                } catch (NumberFormatException ignored) {
                }
            }
            reader.close();
        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException(e.getMessage().concat("(error in getBatteryPercentage)"));
        }
        return -1;
    }

    public static EOperatingSystem getOperatingSystem() {
        String os = System.getProperty("os.name").toLowerCase();
        os = os.contains("win") ? "windows" : os.contains("mac") ? "mac" : (os.contains("nix") || os.contains("nux") || os.contains("aix")) ? "linux" : os.contains("sunos") ? "solaris" : "unknown";

        return switch (os) {
            case "windows" -> EOperatingSystem.WINDOWS;
            case "mac" -> EOperatingSystem.MACOS;
            case "linux" -> EOperatingSystem.LINUX;
            case "solaris" -> EOperatingSystem.SOLARIS;
            default -> EOperatingSystem.UNKNOWN;
        };
    }



    private static ProcessBuilder getProcessBuilderBatteryPercentage(EOperatingSystem operatingSystem) {
        return switch (operatingSystem){
            case LINUX -> new ProcessBuilder("bash", "-c", "upower -i $(upower -e | grep 'BAT') | grep 'percentage' | awk '{print $2}' | sed 's/%//'");
            case WINDOWS -> new ProcessBuilder("cmd.exe", "/c", "WMIC Path Win32_Battery Get EstimatedChargeRemaining");
            case MACOS ->  new ProcessBuilder("bash", "-c", "pmset -g batt | grep -o '[0-9]\\+%' | sed 's/%//'");
            case SOLARIS -> new ProcessBuilder("bash", "-c", "/usr/sbin/prtpicl -v | grep -i capacity | awk '{print $2}'");
            default -> throw  new IllegalStateException("Unexpected value: " + operatingSystem);
        };
    }

    private static ProcessBuilder getProcessBuilderBatteryPercentage(){
        return getProcessBuilderBatteryPercentage(getOperatingSystem());
    }

    private static ProcessBuilder getProcessBuilderChanging(EOperatingSystem operatingSystem) {
        return switch (operatingSystem){
            case LINUX -> new ProcessBuilder("bash", "-c", "upower -i $(upower -e | grep 'BAT') | grep 'state' | awk '{print $2}'");
            case WINDOWS -> new ProcessBuilder("cmd.exe", "/c", "wmic path Win32_Battery get BatteryStatus");
            case MACOS ->  new ProcessBuilder("bash", "-c", "pmset -g batt | grep -o '[0-9]\\+%' | sed 's/%//'");
            case SOLARIS -> new ProcessBuilder("bash", "-c", "/usr/sbin/prtpicl -v | grep -i capacity | awk '{print $2}'");
            default -> throw  new IllegalStateException("Unexpected value: " + operatingSystem);
        };
    }

    private static ProcessBuilder getProcessBuilderChanging(){
        return getProcessBuilderChanging(getOperatingSystem());
    }

}
