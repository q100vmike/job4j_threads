package ru.job4j.fork;

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

    private Integer getIndex(int from, int to) {
        for (int i = from; i < to; i++) {
            if (array.get(i).equals(obj)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected Integer compute() {
        int len = to - from;

        if (len <= 10 && len >= 1) {
            return getIndex(from, to);
        }

        int middle = (from + to) / 2;

        ParallelIndexSearch leftSort = new ParallelIndexSearch(array, obj, from, middle);
        ParallelIndexSearch rightSort = new ParallelIndexSearch(array, obj, middle, to);

        leftSort.fork();
        Integer rightResult = rightSort.compute();
        Integer leftResult = (Integer) leftSort.join();

        return Math.max(leftResult, rightResult);
    }

    public static <T, U> Integer goSearch(List<T> array, U obj) {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        ParallelIndexSearch search = new ParallelIndexSearch(array, obj, 0, array.size());
        return (Integer) pool.invoke(search);
    }
}
