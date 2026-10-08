package org.example;

import java.util.Scanner;

public class Main {

    private static final String DEVELOPER_NAME = "Лотов Марк Эдуардович";
    private static final String DEVELOPER_GROUP = "РИ-250911";
    private static final String PROGRAM_VERSION = "v0.6";

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMainMenu();
            Integer choice = readMenuChoice(0, 3);
            if (choice == null) {
                System.out.println("\nВвод завершён. выход из программы");
                break;
            }
            switch (choice) {
                case 1 -> runCalculationMenu();
                case 2 -> showProgramInfo();
                case 3 -> showDeveloperInfo();
                case 0 -> {
                    System.out.println("Выход из программы");
                    running = false;
                }
                default -> System.out.println("Нет такого пункта");
            }
        }
    }

    public static void showMainMenu() {
        System.out.println("\n======== Главное меню =======");
        System.out.println("1. Выполнить расчёт");
        System.out.println("2. Инфомация о программе");
        System.out.println("3. Информация о разработчике");
        System.out.println("0. Выход");
    }

    public static void runCalculationMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Выбор расчёта ---");
            System.out.println("1. Задача 1: сумма ряда");
            System.out.println("2. Задача 2: степени A от 1 до N");
            System.out.println("3. Задача 3: есть ли в N нечётные цифры");
            System.out.println("4. Задача 4: расстояние между двумя точками");
            System.out.println("0. Назад в гланвое меню");
            Integer choice = readMenuChoice(0, 4);
            if (choice == null || choice == 0) {
                back = true;
            } else {
                switch (choice) {
                    case 1 -> runTask1Dialog();
                    case 2 -> runTask2Dialog();
                    case 3 -> runTask3Dialog();
                    case 4 -> runTask4Dialog();
                    default -> System.out.println("Нет такого пункта.");
                }
            }
        }
    }

    public static void showProgramInfo() {
        System.out.println("\n--- информация о программе ---");
        System.out.println("Версия " + PROGRAM_VERSION);
        System.out.println("Задачи:");
        System.out.println("1. Сумма ряда y = 1 + 1/2 + 1/3 ... + 1/N");
        System.out.println("2. Все целые степени A от 1 до N");
        System.out.println("3. Есть ли в записи N нечётные цифры");
        System.out.println("4. Расстояние между 2 точками");
        System.out.println("Меню реализовано на цикле while, ввод с проверкой и повтором");
        System.out.println("программа разбита на статичиские методы.");
    }

    public static void showDeveloperInfo() {
        System.out.println("\n--- Информация о разработчике ---");
        System.out.println("Разработчик: " + DEVELOPER_NAME);
        System.out.println(DEVELOPER_GROUP);
    }

    public static void runTask1Dialog() {
        System.out.println("\nЗадача 1. Сумма ряда 1 + 1/2 + ... + 1/N");
        Integer n = readInt("Введите целое N > 0 или q для отмены: ", 1);
        if (n == null) {
            System.out.println("Ввод отменён. Возврат в меню.");
            return;
        }
        double y = calculateSeriesSum(n);
        printSeriesSumResult(n, y);
    }

    public static void runTask2Dialog() {
        System.out.println("\nЗадача 2. Степени числа A от 1 до N");
        double[] params = readPowersParams();
        if (params == null) {
            System.out.println("Ввод отменён. Возврат в меню.");
            return;
        }
        double[] powers = calculateIntegerPowers(params);
        printIntegerPowers(powers);
    }

    public static void runTask3Dialog() {
        System.out.println("\nЗадача 3. Есь ли в записи N нечётные цифры");
        Integer n = readInt("Введите целое N > 0 или 'q' для отмены: ", 1);
        if (n == null) {
            System.out.println("Ввод отменён. Возврат в меню.");
            return;
        }
        printHasOddDigitsResult(n, hasOddDigits(n));
    }

    public static void runTask4Dialog() {
        System.out.println("\nЗадача 4. Расстояние между точками (x1, y1) и (x2, y2)");
        double[] p = readPointsParams();
        if (p == null) {
            System.out.println("Ввод отменён. Возврат в меню.");
            return;
        }
        double d = pointsDistance(p);
        printPointsResult(p, d);
    }

    public static double[] readPowersParams() {
        Double a = readDouble("Введти вещественное A или 'q' для отмены: ");
        if (a == null) {
            return null;
        }
        Integer n = readInt("Введите целое N > 0 или 'q' для отмены: ", 1);
        if (n == null) {
            return null;
        }
        return new double[]{a, n};
    }

    public static double[] readPointsParams() {
        Double x1 = readDouble("Введите x1 (или 'q' для отмены): ");
        if (x1 == null) return null;
        Double y1 = readDouble("Введите y1 (или 'q' для отмены): ");
        if (y1 == null) return null;
        Double x2 = readDouble("Введите x2 (или 'q' для отмены): ");
        if (x2 == null) return null;
        Double y2 = readDouble("Введите y2 (или 'q' для отмены): ");
        if (y2 == null) return null;
        return new double[]{x1, y1, x2, y2};
    }

    public static Integer readMenuChoice(int min, int max) {
        while (true) {
            System.out.print("Выберите пункт [" + min + "-" + max + "]: ");
            if (!SCANNER.hasNextLine()) {
                return null;
            }
            String line = SCANNER.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value < min || value > max) {
                    System.out.println("Ошибка: введите число от " + min + " до " + max + ".");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: '" + line + "' - не целое число. Повторите ввод.");
            }
        }
    }

    public static Integer readInt(String prompt, int minValue) {
        while (true) {
            System.out.print(prompt);
            if (!SCANNER.hasNextLine()) {
                return null;
            }
            String line = SCANNER.nextLine().trim();
            if (isExitCommand(line)) {
                return null;
            }
            try {
                int value = Integer.parseInt(line);
                if (value < minValue) {
                    System.out.println("Ошибка: число должно быть >= " + minValue + ". Повторите ввод или введите 'q' для отмены.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: '" + line + "' - не целое число. Повторите ввод или введите 'q' для отмены.");
            }
        }
    }

    public static Double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!SCANNER.hasNextLine()) {
                return null;
            }
            String line = SCANNER.nextLine().trim().replace(',', '.');
            if (isExitCommand(line)) {
                return null;
            }
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: '" + line + "' - не число. Повторите ввод или введите 'q' для отмены.");
            }
        }
    }

    public static boolean isExitCommand(String line) {
        return line.equalsIgnoreCase("q");
    }

    public static void validateSeriesSumArgs(int n) {
        if (n < 1)
            throw new IllegalArgumentException("N должно быть > 0");
    }

    public static double calculateSeriesSum(int n) {
        validateSeriesSumArgs(n);

        double sum = 0.0;
        for (int i = 1; i <= n; i++) {
            sum += 1.0 / i;
        }
        return sum;
    }

    public static void printSeriesSumResult(int n, double y) {
        System.out.println("Результат: y = " + y);
    }

    public static void validatePowersParams(double[] params) {
        if (params == null || params.length != 2)
            throw new IllegalArgumentException("нужно 2 числа: A и N");
        if (!Double.isFinite(params[0]))
            throw new IllegalArgumentException("A должно быть конечным числом");
        if (params[1] < 1)
            throw new IllegalArgumentException("N должно быть > 0");
    }

    public static double[] calculateIntegerPowers(double[] params) {
        validatePowersParams(params);
        double a = params[0];
        int n = (int) params[1];

        var powers = new double[n];
        double power = 1.0;
        for (var i = 0; i < n; i++) {
            power *= a;
            powers[i] = power;
        }
        return powers;
    }

    public static void printIntegerPowers(double[] powers) {
        for (var power : powers) {
            System.out.println(power);
        }
    }

    public static void validateHasOddDigitsArgs(int n) {
        if (n < 1)
            throw new IllegalArgumentException("N должно быть > 0");
    }

    public static boolean hasOddDigits(int n) {
        validateHasOddDigitsArgs(n);

        while (n > 0) {
            int digit = n % 10;
            if (digit % 2 == 1) {
                return true;
            }
            n /= 10;
        }
        return false;
    }

    public static void printHasOddDigitsResult(int n, boolean result) {
        System.out.println("результат: " + result);
    }

    public static void validatePointsParams(double[] p) {
        if (p == null || p.length != 4)
            throw new IllegalArgumentException("нужно 4 числа: x1, y1, x2, y2");
        for (var v : p) {
            if (!Double.isFinite(v))
                throw new IllegalArgumentException("координаты должны быть конечными числами");
        }
    }

    public static double pointsDistance(double[] p) {
        validatePointsParams(p);
        return Math.sqrt(
                Math.pow((p[2] - p[0]), 2) + Math.pow((p[3] - p[1]), 2)
        );
    }

    public static void printPointsResult(double[] p, double d) {
        System.out.println("Результат: растояние = " + d);
    }
}
