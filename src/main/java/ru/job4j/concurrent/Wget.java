package ru.job4j.concurrent;
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
        /* Скачать файл*/
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
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        checkArguments(args);

        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}