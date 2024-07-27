package com.batery.controller;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;

public class MusicController {
    private static MusicController instance;
    private Clip clip;

    private MusicController() {}
    public static MusicController getInstance() {
        if (instance == null) {
            instance = new MusicController();
        }
        return instance;
    }
    public void play(File file) throws Exception {
        if (file.exists()) {
            executeSong(file);
        }else {
            throw new FileNotFoundException("File not found");
        }
    }
    //file=new File("src/main/java/com/batery/asset/music/fondo1.wav");
    public void play(int obtion) throws Exception {
        File file = null;
        switch (obtion) {
            case 1-> file=createTemporalFile("/asset/music/fondo1.wav");
            case 2-> file=createTemporalFile("/asset/music/fondo2.wav");
            case 3-> file=createTemporalFile("/asset/music/fondo3.wav");
            case 4-> file=createTemporalFile("/asset/music/fondo4.wav");
            case 5-> file=createTemporalFile("/asset/music/fondo5.wav");
            case 6-> file=createTemporalFile("/asset/music/fondo6.wav");
            case 7-> file=createTemporalFile("/asset/music/fondo7.wav");
            case 8-> file=createTemporalFile("/asset/music/fondo8.wav");
            default-> throw new FileNotFoundException("File not found");
        }
        executeSong(file);
    }
    private void executeSong(File file) throws Exception {
        //Windows
        /*clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(file));
        clip.start();*/
        //ubuntu
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    }
    public void pause() {
        if (clip!=null) {
            clip.stop();
            clip.close();
        }
    }

    private File createTemporalFile(String urlResource) throws Exception {
        try (InputStream is = getClass().getResourceAsStream(urlResource)) {
            File tempFile = Files.createTempFile("temp_", ".wav").toFile();
            tempFile.deleteOnExit();

            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
            }

            return tempFile;
        }
    }

/*
    public static void main(String[] args) throws Exception {
        int cont=0;
        getInstance().play(new File("./src/main/resources/music/fondo1.wav"));
        while (!Thread.currentThread().isInterrupted()){
            Thread.sleep(100);
            if(cont==20){
                getInstance().pause();
            }
            if (cont==50){
                getInstance().play(3);
                cont=0;
            }
            cont++;
            System.out.println(cont);

        }
    }*/

    public static void main(String[] args) throws Exception {
        MusicController.getInstance().pause();
    }
}
