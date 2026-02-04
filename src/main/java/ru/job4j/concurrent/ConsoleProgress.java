package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable{

    @Override
    public void run() {

        var process = new char[] {'-', '\\', '|', '/'};

        Thread thread = new Thread(
                () -> {
                    int count = 0;
                    while (!Thread.currentThread().isInterrupted()) {

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.print("\r load: " + process[count]);
                        if (++count == 4) {count = 0;}
                    }

                }
        );
    }

    public static void main(String[] args) throws InterruptedException {
        ConsoleProgress consoleProgress = new ConsoleProgress();
        Thread thread = new Thread(consoleProgress);
        thread.start();

//        Thread thread = new Thread(
//                () -> {
//                    int count = 0;
//                    while (!Thread.currentThread().isInterrupted()) {
//                        System.out.println(count++);
//                    }
//                }
//        );
//        thread.start();
//        Thread.sleep(1000);
//        thread.interrupt();
    }
}
