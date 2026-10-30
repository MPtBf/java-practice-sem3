package org.example.intlist;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Вопрос 1 — IntList")
class Question1Test {

    @Test
    @DisplayName("конструктор создаёт список нужной длины")
    void constructorCreatesListOfRequestedLength() {
        assertEquals(5, new IntList(5).size());
    }

    @Test
    @DisplayName("длина 0 допустима")
    void zeroLengthIsAllowed() {
        assertEquals(0, new IntList(0).size());
    }

    @Test
    @DisplayName("отрицательная длина недопустима")
    void negativeLengthIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new IntList(-1));
    }

    @Test
    @DisplayName("значение записывается и читается по индексу")
    void valueCanBeWrittenAndReadByIndex() {
        IntList list = new IntList(3);
        list.set(0, 10);
        list.set(1, -5);
        list.set(2, 42);

        assertEquals(10, list.get(0));
        assertEquals(-5, list.get(1));
        assertEquals(42, list.get(2));
    }

    @Test
    @DisplayName("повторная запись перезаписывает значение")
    void valueCanBeOverwritten() {
        IntList list = new IntList(1);
        list.set(0, 1);
        list.set(0, 2);

        assertEquals(2, list.get(0));
    }

    @Test
    @DisplayName("недопустимый индекс при записи и чтении")
    void invalidIndexIsRejected() {
        IntList list = new IntList(3);

        assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(3, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));
    }

    @Test
    @DisplayName("минимум и максимум")
    void minAndMaxAreCalculated() {
        IntList list = new IntList(5);
        list.set(0, 7);
        list.set(1, -3);
        list.set(2, 0);
        list.set(3, 12);
        list.set(4, -8);

        assertEquals(-8, list.min());
        assertEquals(12, list.max());
    }

    @Test
    @DisplayName("среднее арифметическое")
    void averageIsCalculated() {
        IntList list = new IntList(4);
        list.set(0, 1);
        list.set(1, 2);
        list.set(2, 3);
        list.set(3, 4);

        assertEquals(2.5, list.average(), 1e-9);
    }

    @Test
    @DisplayName("расчёты для пустого списка недопустимы")
    void calculationsForEmptyListAreRejected() {
        IntList list = new IntList(0);

        assertThrows(IllegalStateException.class, list::min);
        assertThrows(IllegalStateException.class, list::max);
        assertThrows(IllegalStateException.class, list::average);
    }

    @Test
    @DisplayName("print() выводит элементы через пробел")
    void printOutputsAllValues() {
        IntList list = new IntList(3);
        list.set(0, 5);
        list.set(1, -2);
        list.set(2, 10);

        String output = captureOutput(list::print);
        assertArrayEquals(new String[]{"5", "-2", "10"}, output.trim().split("\\s+"));
    }

    @Test
    @DisplayName("массив является закрытым полем")
    void backingArrayIsPrivate() {
        Field[] fields = IntList.class.getDeclaredFields();

        assertTrue(Arrays.stream(fields).anyMatch(field -> field.getType() == int[].class));
        assertTrue(Arrays.stream(fields).allMatch(field -> Modifier.isPrivate(field.getModifiers())));
    }

    private static String captureOutput(Runnable action) {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));
            action.run();
        } finally {
            System.setOut(original);
        }
        return buffer.toString(StandardCharsets.UTF_8);
    }
}
