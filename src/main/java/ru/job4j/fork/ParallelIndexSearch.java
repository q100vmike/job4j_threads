package ru.job4j.fork;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearch<T> extends RecursiveTask<Integer> {

    private final List<?> array;
    private final T obj;
    private final int from;
    private final int to;
    //public T getObj() { return obj; }

    public ParallelIndexSearch(List<?> array, T obj, int from, int to) {
        this.array = array;
        this.obj = obj;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        int len = array.size();
        int j = -1;

        if (len <= 10) {
            for (int i = 0; i < len; i++) {
                if (array.get(i).equals(obj)) {
                    return i;
               }
            }
        }

        int middle = (from + to) / 2;
        // создаем задачи для сортировки частей
        ParallelIndexSearch leftSort = new ParallelIndexSearch(array, obj, from, middle);
        ParallelIndexSearch rightSort = new ParallelIndexSearch(array, obj, middle + 1, to);
        // производим деление.
        // оно будет происходить, пока в частях не останется по одному элементу
        leftSort.fork();
        rightSort.fork();


        return j;
    }
}
