package ru.job4j.pool;

import ru.job4j.queue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Thread.sleep;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(5);

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            Thread th = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        var r = tasks.poll();
                        if (r != null) {
                            r.run();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            th.start();
            threads.add(th);
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() throws InterruptedException {
        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).interrupt();
        }
        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).join();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Runnable job = new Runnable() {
            @Override
            public void run() {
                System.out.println("run" + Thread.currentThread().getName());
            }
        };
        ThreadPool pool = new ThreadPool();
        for (int i = 0; i < 10; i++) {
            pool.work(job);
        }
        pool.shutdown();
    }
}