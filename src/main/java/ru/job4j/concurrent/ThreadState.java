package ru.job4j.concurrent;

public class ThreadState {

    public static void main(String[] args) throws InterruptedException {

        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );

        System.out.println(first.getState());
        first.start();
        System.out.println(second.getState());
        second.start();

        while (first.getState() != Thread.State.TERMINATED || second.getState() != Thread.State.TERMINATED) {
            Thread.currentThread().sleep(100);
        }
        System.out.println("Работа завершена");

    }
}
