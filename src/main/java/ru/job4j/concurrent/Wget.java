package ru.job4j.concurrent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.regex.Pattern;
import  java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        String fileName = "tmp.xml";
        int lastAtIndex = url.lastIndexOf('/');
        if (lastAtIndex != -1) {
            fileName = "_" + url.substring(lastAtIndex + 1);
        }
        var file = new File(fileName);

        try (var input = new URL(url).openStream(); var output = new FileOutputStream(file)) {
            long milis = (System.currentTimeMillis() - startAt);
            System.out.println("Open connection: " + milis + " ms");
            var dataBuffer = new byte[512];
            int bytesRead = 0;
            float bytesReadProgress = 0;

            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                bytesReadProgress += bytesRead;
                if (bytesReadProgress >= speed) {
                    var t = (System.currentTimeMillis() - milis);
                    if ((System.currentTimeMillis() - milis) < 1000) {
                        System.out.println("milis =" + (System.currentTimeMillis() - milis) + " sleep= " +((bytesReadProgress / milis)) / speed);
                        Thread.sleep((long) ((bytesReadProgress / milis)) / speed);
                    }
                }
                output.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void checkArguments(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("No Arguments found!");
        }

        Pattern pattern = Pattern.compile("^\\d+");
        if (!pattern.matcher(args[1]).matches()) {
            throw new IllegalArgumentException("Invalid 2'nd Argument!");
        }

        try {
            new URL(args[0]).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new IllegalArgumentException("Invalid 1'st Argument!");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        checkArguments(args);
        Thread wget = new Thread(new Wget(args[0], Integer.parseInt(args[1])));
        wget.start();
        wget.join();
    }
}