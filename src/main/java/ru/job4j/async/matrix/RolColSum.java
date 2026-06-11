package ru.job4j.async.matrix;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public Sums() {
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        public int getColSum() {
            return colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        int sumCol = 0;
        int sumRow = 0;
        Sums[] sums = new Sums[matrix.length];


        for (int i = 0; i < matrix.length; i++) {
            sumRow = 0;
            sumCol = 0;
            for (int j = 0; j < matrix.length; j++) {
                sumRow = sumRow + matrix[i][j];
                sumCol = sumCol + matrix[j][i];
            }
            sums[i] = new Sums();
            sums[i].setRowSum(sumRow);
            sums[i].setColSum(sumCol);
        }
        return sums;
    }

    public static CompletableFuture<Sums[]> getTaskSumi(int[][] matrix, int startIndex) {
        return CompletableFuture.runAsync(
                () -> {
                    for (int i = 0; i < matrix.length; i++) {
                        sumRow.set(0);
                        sumCol.set(0);
                        for (int j = 0; j < matrix.length; j++) {
                            sumRow.set(sumRow.get() + matrix[i][j]);
                            sumCol.set(sumCol.get() + matrix[j][i]);
                        }
                        sums[i] = new Sums();
                        sums[i].setRowSum(sumRow.get());
                        sums[i].setColSum(sumCol.get());
                    }
                }
        );
    }

    public static Sums[] asyncSum(int[][] matrix) {
        AtomicInteger sumCol = new AtomicInteger();
        AtomicInteger sumRow = new AtomicInteger();
        int len = matrix.length;
        Sums[] sums = new Sums[len];

        CompletableFuture.runAsync(
                () -> {
                    for (int i = 0; i < matrix.length; i++) {
                        sumRow.set(0);
                        sumCol.set(0);
                        for (int j = 0; j < matrix.length; j++) {
                            sumRow.set(sumRow.get() + matrix[i][j]);
                            sumCol.set(sumCol.get() + matrix[j][i]);
                        }
                        sums[i] = new Sums();
                        sums[i].setRowSum(sumRow.get());
                        sums[i].setColSum(sumCol.get());
                    }
//                    System.out.println("Сын: Мам/Пам, я пошел выносить мусор");
//                    try {
//                        TimeUnit.SECONDS.sleep(5);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println("Сын: Мам/Пап, я вернулся!");
                }
        );

        return new Sums[matrix.length];
    }

    public static void main(String[] args) {
        int[][] array = {
                {5, 0, 2},
                {3, 3, 2},
                {0, 1, 2}
        };
        Sums[] sums = RolColSum.sum(array);
        for (int i = 0; i < array.length; i++) {
            System.out.println(i + ":" + "->" + sums[i].getRowSum() + "==" + sums[i].getColSum() + "|");
        }
    }
}