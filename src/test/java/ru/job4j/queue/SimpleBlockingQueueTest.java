package ru.job4j.queue;

import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static java.lang.Thread.sleep;
import static org.assertj.core.api.Assertions.assertThat;

class SimpleBlockingQueueTest {

    @Test
    public void whenFetchAllThenGetItString() throws InterruptedException {
        final CopyOnWriteArrayList<String> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<String> queue = new SimpleBlockingQueue<>(4);
        Thread producer = new Thread(
                () -> {
                    IntStream.range(0, 5).forEach(i -> {
                        try {
                            queue.offer("el" + i);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).containsExactly("el0", "el1", "el2", "el3", "el4");
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(6);
        Thread producer = new Thread(
                () -> {
                    IntStream.range(0, 5).forEach(i -> {
                        try {
                            queue.offer(i);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).containsExactly(0, 1, 2, 3, 4);
    }

    @Test
    void whenQueueWorked() throws InterruptedException {

        var queue = new SimpleBlockingQueue<Integer>(3);

        Thread producer  = new Thread(() -> {
            try {
                queue.offer(1);
                queue.offer(2);
                queue.offer(3);
                queue.offer(4);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                queue.poll();
                queue.poll();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        producer.start();
        consumer.start();

        consumer.join();
        producer.join();

        assertThat(queue.size()).isEqualTo(2);
        assertThat(queue.poll()).isEqualTo(3);
        assertThat(queue.poll()).isEqualTo(4);
    }

    @Test
    void whenQueueWorkedOffer() throws InterruptedException {

        AtomicBoolean offered = new AtomicBoolean(false);
        var queue = new SimpleBlockingQueue<Integer>(3);

        Thread producer  = new Thread(() -> {
            try {
                queue.offer(1);
                queue.offer(2);
                queue.offer(3);
                queue.offer(4);
                offered.set(true);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        producer.start();

        assertThat(offered.get()).isFalse();
        queue.poll();
        sleep(100);
        assertThat(offered.get()).isTrue();

        producer.join();

        assertThat(queue.size()).isEqualTo(3);
        assertThat(queue.poll()).isEqualTo(2);
        assertThat(queue.poll()).isEqualTo(3);
        assertThat(queue.poll()).isEqualTo(4);
    }

    @Test
    void whenQueueWorkedPoll() throws InterruptedException {

        AtomicBoolean offered = new AtomicBoolean(false);
        var queue = new SimpleBlockingQueue<Integer>(3);

        Thread consumer  = new Thread(() -> {
            try {
                queue.poll();
                offered.set(true);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        consumer.start();
        assertThat(offered.get()).isFalse();
        queue.offer(1);
        sleep(100);
        assertThat(offered.get()).isTrue();

        consumer.join();

        assertThat(queue.size()).isEqualTo(0);
    }
}