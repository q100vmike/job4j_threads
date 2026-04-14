package queue;

import org.junit.jupiter.api.Test;
import ru.job4j.queue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleBlockingQueueTest {

    @Test
    void whenAdd() throws InterruptedException {

        var queue = new SimpleBlockingQueue<Integer>(3);
//
//        queue.offer(1);
//        queue.offer(2);
//        queue.offer(3);
//        System.out.println(queue.size());
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




//        Set<Integer> rsl = new TreeSet<>();
//        list.iterator().forEachRemaining(rsl::add);
//        assertThat(rsl).hasSize(2).containsAll(Set.of(1, 2));
    }
}