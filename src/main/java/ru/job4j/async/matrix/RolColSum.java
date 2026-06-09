package ru.job4j.async.matrix;

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
            for (int j = 0; j < matrix.length; j++) {
                sumRow = sumRow + matrix[i][j];
            }
            sums[i] = new Sums();
            sums[i].setRowSum(sumRow);
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                sumCol = sumCol + matrix[i][j];
            }
            sums[i].setColSum(sumCol);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) {
        //Sums[] sums = new Sums[matrix.length];
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
            System.out.println(sums[i].getRowSum() + "==" + sums[i].getColSum());
        }

        //стр 18
        //стб 18
    }
}