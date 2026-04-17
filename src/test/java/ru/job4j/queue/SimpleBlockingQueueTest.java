package ru.job4j.queue;

import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicBoolean;
import ru.job4j.queue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.Queue;

import static java.lang.Thread.sleep;
import static org.assertj.core.api.Assertions.assertThat;

class SimpleBlockingQueueTest {

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