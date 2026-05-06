package ru.job4j.pool;

import org.testng.util.TimeUtils;
import ru.job4j.queue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(5);

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(() -> {
                try {
                    tasks.poll();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }));
        }
    }

    public void work(Runnable job) {

    }

    public void shutdown() {

    }
}