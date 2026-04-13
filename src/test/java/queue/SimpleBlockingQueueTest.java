package queue;

import org.junit.jupiter.api.Test;
import ru.job4j.cash.Account;
import ru.job4j.cash.AccountStorage;

import java.util.Set;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    void whenAdd() throws InterruptedException {
        var queue = new SimpleBlockingQueue<Integer>(5);

        Thread first = new Thread(() -> {
            try {
                queue.offer(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread second = new Thread(() -> {
            try {
                queue.offer(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        first.start();
        second.start();
        first.join();
        second.join();

//        Set<Integer> rsl = new TreeSet<>();
//        list.iterator().forEachRemaining(rsl::add);
//        assertThat(rsl).hasSize(2).containsAll(Set.of(1, 2));
    }
}