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
        var file = new File("tmp.xml");
        try (var input = new URL(url).openStream();
             var output = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            var dataBuffer = new byte[512];
            int bytesRead = 0;
            float bytesReadProgress = 0;
            long downloadAt = System.nanoTime();

            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                bytesReadProgress += bytesRead;
                if (bytesReadProgress >= speed) {
                    long nano = (System.nanoTime() - downloadAt);
                    float miliSec = (System.nanoTime() - downloadAt) * 1000000f;
                    System.out.println("bytesReadProgress=" + bytesReadProgress + " nano=" + (System.nanoTime() - downloadAt) + " miliSec=" + miliSec);
                    if (nano < 1000000000f) {
                        int timer = (int) (((bytesReadProgress / nano)) * 1000000f);
                        System.out.println("timer=" + timer + " sleep=" + timer / speed);
                        Thread.sleep( timer / speed);
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