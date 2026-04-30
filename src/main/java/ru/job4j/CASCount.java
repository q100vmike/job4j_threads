package ru.job4j;

import net.jcip.annotations.ThreadSafe;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public CASCount(int value) {
        count.set(value);
    }

    public void increment() {
        int temp;
        int ref;
        do {
            temp = count.get();
            ref = temp++;
        } while (!count.compareAndSet(temp, ref));
    }

    public int get() {
        return count.get();
    }

    public static void main(String[] args) {
        CASCount casCount = new CASCount(3);

        casCount.increment();

    }
}