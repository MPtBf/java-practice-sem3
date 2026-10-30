package org.example.intlist;

/**
 * Список целых чисел на основе закрытого массива.
 *
 * <p>Правила, на которые рассчитаны тесты:</p>
 * <ul>
 *     <li>длина массива задаётся в конструкторе; отрицательная длина недопустима;</li>
 *     <li>длина 0 допустима, но расчёт минимума, максимума и среднего для пустого списка
 *     должен выбрасывать {@link IllegalStateException};</li>
 *     <li>индекс должен принадлежать диапазону {@code [0, size())};</li>
 *     <li>{@link #print()} печатает элементы через пробел в одной строке.</li>
 * </ul>
 */
public class IntList {

    private int[] values;

    /**
     * @throws IllegalArgumentException если длина отрицательная
     */
    public IntList(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length should be >= 0");
        }
        values = new int[length];
    }

    public int size() {
        return values.length;
    }

    /**
     * @throws IndexOutOfBoundsException если индекс вне диапазона {@code [0, size())}
     */
    public void set(int index, int value) {
        if (index < 0 || index >= values.length) {
            throw new IndexOutOfBoundsException("list index should be in the range [0; " + (values.length - 1) + "]");
        }
        values[index] = value;
    }

    /**
     * @throws IndexOutOfBoundsException если индекс вне диапазона {@code [0, size())}
     */
    public int get(int index) {
        if (index < 0 || index >= values.length) {
            throw new IndexOutOfBoundsException("list index should be in the range [0; " + (values.length - 1) + "]");
        }
        return values[index];
    }

    /**
     * @throws IllegalStateException если список пуст
     */
    public int min() {
        if (values.length == 0) {
            throw new IllegalStateException("cannot get minimum when empty");
        }
        int min_value = Integer.MAX_VALUE;
        for (int value : values) {
            min_value = Math.min(min_value, value);
        }
        return min_value;
    }

    /**
     * @throws IllegalStateException если список пуст
     */
    public int max() {
        if (values.length == 0) {
            throw new IllegalStateException("cannot get maximum when empty");
        }
        int max_value = Integer.MIN_VALUE;
        for (int value : values) {
            max_value = Math.max(max_value, value);
        }
        return max_value;
    }

    /**
     * @throws IllegalStateException если список пуст
     */
    public double average() {
        if (values.length == 0) {
            throw new IllegalStateException("cannot get average when empty");
        }
        int sum = 0;
        for (int value : values) {
            sum += value;
        }
        return sum / (double) values.length;
    }

    /**
     * Печатает все элементы через пробел в одной строке.
     */
    public void print() {
        for (int value : values) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
}
