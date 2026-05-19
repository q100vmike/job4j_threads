package ru.job4j.fork;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearch extends RecursiveTask<Integer>{

    private final List<?> array;
    private final int index;

    public ParallelIndexSearch(List<?> array, int from, int to, int index) {
        this.array = array;
        this.index = index;
    }

    @Override
    protected Integer compute() {
        return 0;

    }
}
