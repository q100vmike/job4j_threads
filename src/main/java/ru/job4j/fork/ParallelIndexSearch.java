package ru.job4j.fork;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearch<T> extends RecursiveTask<Integer> {

    private final List<?> array;
    private final T obj;

    //public T getObj() { return obj; }

    public ParallelIndexSearch(List<?> array, T obj) {
        this.array = array;
        this.obj = obj;
    }

    @Override
    protected Integer compute() {
        int len = array.size();

        if (len <= 10) {
            for (int i = 0; i < len; i++) {
                if (array.get(i).equals(obj)) {
                    return i;
               }
            }
        }

        return 0;
    }
}
