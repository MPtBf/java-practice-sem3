package org.example;

import org.example.calculations.Calculation;
import org.example.calculations.DistanceCalculation;
import org.example.calculations.SeriesSumCalculation;
import org.example.intlist.IntList;
import org.example.tasks.PersonalTask;
import org.example.tasks.StudyTask;
import org.example.tasks.Task;
import org.example.tasks.TaskStatus;
import org.example.tasks.WorkTask;

import java.time.LocalDate;

/**
 * Точка входа для демонстрации заданий практики 04.
 *
 * <p>Предметная область: учебные, рабочие и личные задачи, а также расчёты
 * из практики 02 (сумма ряда и расстояние между точками).</p>
 *
 * <p>Инкапсуляция: все поля классов закрыты ({@code private}), доступ к ним
 * выполняется через конструкторы и геттеры. Наследование: {@link StudyTask},
 * {@link WorkTask} и {@link PersonalTask} наследуют {@link Task};
 * {@link SeriesSumCalculation} и {@link DistanceCalculation} наследуют
 * {@link Calculation}. Переопределение: в наследниках переопределены
 * {@code getDescription()}, {@code getWeight()}, {@code toString()},
 * {@code equals()} и {@code hashCode()}. Полиморфизм: объекты хранятся в
 * массивах родительского типа, а общие методы вызываются в одном цикле.</p>
 */
public class Main {

    public static void main(String[] args) {
        intListDemonstration();
        taskDemonstration();
        calculationDemonstration();
    }

    private static void intListDemonstration() {
        System.out.println("\n\n=== Вопрос 1: IntList ===");

        System.out.println("\n--- init ---");
        IntList list = new IntList(6);
        list.print();

        System.out.println("\n--- get size ---");
        System.out.println("size: " + list.size());

        System.out.println("\n--- put some values ---");
        list.set(0, 50);
        list.set(1, 40);
        list.set(2, 30);
        list.set(3, 40);
        list.set(4, 50);
        list.set(5, 60);
        list.print();

        System.out.println("\n--- get some values ---");
        System.out.println("3rd element is: " + list.get(2));

        System.out.println("\n--- trigger index exception ---");
        try {
            System.out.println("trying to set value to index 100...");
            list.set(100, 1);
            System.out.println("success");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("threw error: " + e.getLocalizedMessage());
        }

        System.out.println("\n--- min/max/avg ---");
        System.out.println("min: " + list.min());
        System.out.println("max: " + list.max());
        System.out.println("avg: " + list.average());

        System.out.println("\n--- trigger min/max/avg exception ---");
        IntList list2 = new IntList(0);
        try {
            System.out.println("trying to get maximum of list with length " + list2.size() + "...");
            list2.max();
            System.out.println("success");
        } catch (IllegalStateException e) {
            System.out.println("threw error: " + e.getLocalizedMessage());
        }
    }

    private static void taskDemonstration() {
        System.out.println("\n\n=== Вопросы 2 и 4: задачи ===");

        LocalDate dueDate = LocalDate.of(2026, 11, 1);

        // Переменные родительского типа хранят объекты разных наследников.
        Task study = new StudyTask("Алгебра", "Решить задачи", 3, dueDate,
                TaskStatus.NEW, "Математика", 4);
        Task work = new WorkTask("Отчёт", "Подготовить отчёт", 2, dueDate.plusDays(2),
                TaskStatus.IN_PROGRESS, "Учебный проект", 5);
        Task personal = new PersonalTask("Уборка", "Убраться дома", 4, dueDate.plusDays(3),
                TaskStatus.DONE, "Дом", true);

        System.out.println("\n--- полиморфный getDescription ---");
        System.out.println(study.getDescription());
        System.out.println(work.getDescription());
        System.out.println(personal.getDescription());

        // Вопрос 4: массив родительского типа, по два объекта каждого наследника
        Task[] tasks = {
                new StudyTask("Учебная 1", "Первая учебная", 3, dueDate,
                        TaskStatus.NEW, "Математика", 4),
                new StudyTask("Учебная 2", "Вторая учебная", 1, dueDate.plusDays(1),
                        TaskStatus.IN_PROGRESS, "Физика", 2),
                new WorkTask("Рабочая 1", "Первая рабочая", 2, dueDate.plusDays(2),
                        TaskStatus.DONE, "Проект A", 5),
                new WorkTask("Рабочая 2", "Вторая рабочая", 5, dueDate.plusDays(3),
                        TaskStatus.NEW, "Проект B", 1),
                new PersonalTask("Личная 1", "Первая личная", 4, dueDate.plusDays(4),
                        TaskStatus.DONE, "Дом", true),
                new PersonalTask("Личная 2", "Вторая личная", 4, dueDate.plusDays(5),
                        TaskStatus.IN_PROGRESS, "Спорт", false)
        };

        System.out.println("\n--- массив Task[] ---");
        int totalWeight = 0;
        for (Task task : tasks) {
            System.out.println(task);
            System.out.println(task.getDescription());
            totalWeight += task.getWeight();
        }

        System.out.println("\n--- общий результат ---");

        System.out.println("Общий вес задач: " + totalWeight);
        System.out.println("Формулы веса: \nStudyTask: priority * plannedHours, "
                + "\nWorkTask: priority * estimatedHours * 2, "
                + "\nPersonalTask: priority * (recurring ? 3 : 1)");

        Task sameStudy = new StudyTask("Алгебра", "Решить задачи", 3, dueDate,
                TaskStatus.NEW, "Математика", 4);

        System.out.println("\n--- equals() и hashCode() ---");
        System.out.println("study.equals(sameStudy): " + study.equals(sameStudy));
        System.out.println("hashCode: " + study.hashCode() + " / " + sameStudy.hashCode());
    }

    private static void calculationDemonstration() {
        System.out.println("\n\n=== Вопрос 3: расчёты ===");

        System.out.println("\n--- массив родительского типа ---");

        // Массив родительского типа содержит объекты разных наследников.
        Calculation[] calculations = {
                new SeriesSumCalculation(5),
                new DistanceCalculation(0, 0, 3, 4)
        };

        for (Calculation calculation : calculations) {
            System.out.println(calculation.toString());
            System.out.println(calculation.getResultDescription());
        }

        System.out.println("\n--- equals() и hashCode() ---");

        Calculation first = new SeriesSumCalculation(5);
        Calculation second = new SeriesSumCalculation(5);

        System.out.println("first.equals(second): " + first.equals(second));
        System.out.println("hashCode: " + first.hashCode() + " / " + second.hashCode());
    }
}
