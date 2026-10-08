package org.example;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        printMatrixReversed(new Object[][]{{"1","2","3"},{"11","22","33"},{"111","222","333"},{"1111","2222","3333"}});
    }

    // Вывести сначала элементы с чётными номерами по возрастанию, затем элементы с нечётными номерами по возрастанию.
    // Условный оператор не использовать.
    public static void printEvenThenOdd(int[] array) {
        if (array == null)
            throw new IllegalArgumentException("массив не должен быть нулёвым");

        for (var i = 0; i < array.length; i += 2) {
            System.out.print(array[i] + " ");
        }
        for (var i = 1; i < array.length; i += 2) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

//    Найти количество участков, на которых элементы массива монотонно убывают.
    public static int countDescendingRegions(double[] array) {
        if (array == null || array.length < 2) return 0;

        int count = 0;

        boolean isDesc = false;
        for (int i = 1; i < array.length; i++) {
            if (!isDesc && array[i] < array[i - 1]) {
                count++;
                isDesc = true;
            }
            else if (isDesc && array[i] >= array[i - 1]) {
                isDesc = false;
            }
        }
        return count;
    }

//    Даны A и B. Сформировать C, где Ck равен максимуму Ak и Bk. Реализовать отдельным статическим методом.
    public static double[] maxArray(double[] a, double[] b) {
        if (a == null || b == null)
            throw new IllegalArgumentException("массивы не должны быть нулёвыми");
        if (a.length != b.length)
            throw new IllegalArgumentException("массивы должны быть равны по длине");

        double[] c = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            var ak = a[i];
            var bk = b[i];
            c[i] = Math.max(ak,bk);
        }
        return c;
    }

//    Вывести матрицу: первая строка слева направо, вторая справа налево, третья слева направо, четвёртая
//    справа налево и т. д.
    public static void printMatrixReversed(Object[][] matrix) {
        var n = matrix.length;
        var m = matrix[0].length;
        if (n == 0 || m == 0)
            throw new IllegalArgumentException("в матрице должна быть хотя бы одна строка и столбец");

        for (var i = 0; i < n; i++) {
            if (i % 2 == 0){
                System.out.println(Arrays.toString(matrix[i]));
            }
            else {
                System.out.print('[');
                for (var j = 0; j < m; j++) {
                    System.out.print(matrix[i][m - j - 1]);
                    if (m - j - 1 != 0)
                        System.out.print(", ");
                }
                System.out.print(']');
                System.out.println();
            }
        }
    }

    // Доработать программу из лабораторной работы №2: применить массивы для хранения параметров вычисления и
    // передачи данных между статическими методами; выделить методы ввода, вычисления, вывода и проверки; сохранить
    // меню и обработку ошибок.
}
