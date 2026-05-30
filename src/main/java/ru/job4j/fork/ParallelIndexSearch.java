package ru.job4j.fork;

import ru.job4j.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearch<T> extends RecursiveTask<Integer> {

    private final List<?> array;
    private final T obj;
    private final int from;
    private final int to;

    public ParallelIndexSearch(List<?> array, T obj, int from, int to) {
        this.array = array;
        this.obj = obj;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        int len = to - from;

        if (len <= 10 && len >= 1) {
            for (int i = from; i < to; i++) {
                if (array.get(i).equals(obj)) {
                    return i;
               }
            }
            return -1;
        }

        int middle = (from + to) / 2;

        ParallelIndexSearch leftSort = new ParallelIndexSearch(array, obj, from, middle);
        ParallelIndexSearch rightSort = new ParallelIndexSearch(array, obj, middle, to);

        leftSort.fork();
        Integer rightResult = rightSort.compute();
        Integer leftResult = (Integer) leftSort.join();

        if (leftResult != -1) {
            return leftResult;
        }
        if (rightResult != -1) {
            return rightResult;
        }
        return -1;

    }
}
