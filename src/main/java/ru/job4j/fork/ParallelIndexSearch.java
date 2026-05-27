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
            return -1;
        }

        int middle = (from + to) / 2;
        // создаем задачи для сортировки частей
        ParallelIndexSearch leftSort = new ParallelIndexSearch(array, obj, from, middle);
        ParallelIndexSearch rightSort = new ParallelIndexSearch(array, obj, middle + 1, to);
        // производим деление.
        // оно будет происходить, пока в частях не останется по одному элементу
        leftSort.fork();
        Integer rightResult = rightSort.compute();
        Integer leftResult = (Integer) leftSort.join();

        // Возвращаем первый найденный индекс
        if (leftResult != -1) return leftResult;
        if (rightResult != -1) return rightResult;
        return -1;

    }

    public static void main(String[] args) {

        List<User> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {

        }
        User user1 = User.of("One");
        User user2 = User.of("Two");
        User user3 = User.of("Three");



        ForkJoinPool pool = ForkJoinPool.commonPool();
        ParallelIndexSearch task = new ParallelIndexSearch<>(list, user3, 0, list.size());
        Integer result = (Integer) pool.invoke(task);
        System.out.println("result= " + result);
    }
}
