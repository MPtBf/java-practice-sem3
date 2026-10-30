package org.example.tasks;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Вопросы 2 и 4 — иерархия Task и массив задач")
class Question2And4Test {

    private static final LocalDate DUE_DATE = LocalDate.of(2026, 11, 1);

    @Test
    @DisplayName("базовые поля заполняются конструктором")
    void baseFieldsAreFilledByConstructor() {
        Task task = studyTask();

        assertEquals("Алгебра", task.getTitle());
        assertEquals("Решить задачи", task.getDescriptionText());
        assertEquals(3, task.getPriority());
        assertEquals(DUE_DATE, task.getDueDate());
        assertEquals(TaskStatus.NEW, task.getStatus());
    }

    @Test
    @DisplayName("собственные поля наследников заполняются конструктором")
    void subclassFieldsAreFilledByConstructor() {
        assertEquals("Математика", studyTask().getSubject());
        assertEquals(4, studyTask().getPlannedHours());
        assertEquals("Учебный проект", workTask().getProject());
        assertEquals(5, workTask().getEstimatedHours());
        assertEquals("Дом", personalTask(true).getCategory());
        assertTrue(personalTask(true).isRecurring());
    }

    @Test
    @DisplayName("вес учебной задачи: priority * plannedHours")
    void studyTaskWeight() {
        assertEquals(12, studyTask().getWeight());
    }

    @Test
    @DisplayName("вес рабочей задачи: priority * estimatedHours * 2")
    void workTaskWeight() {
        assertEquals(20, workTask().getWeight());
    }

    @Test
    @DisplayName("вес личной задачи: priority * (recurring ? 3 : 1)")
    void personalTaskWeight() {
        assertEquals(12, personalTask(true).getWeight());
        assertEquals(4, personalTask(false).getWeight());
    }

    @Test
    @DisplayName("getDescription() полиморфно добавляет поля наследника")
    void descriptionIsPolymorphic() {
        StudyTask study = studyTask();
        WorkTask work = workTask();
        PersonalTask personal = personalTask(true);

        assertBaseFieldsIn(study.getDescription(), study);
        assertBaseFieldsIn(work.getDescription(), work);
        assertBaseFieldsIn(personal.getDescription(), personal);

        assertTrue(study.getDescription().contains("Математика"));
        assertTrue(study.getDescription().contains(String.valueOf(study.getPlannedHours())));
        assertTrue(work.getDescription().contains("Учебный проект"));
        assertTrue(work.getDescription().contains(String.valueOf(work.getEstimatedHours())));
        assertTrue(personal.getDescription().contains("Дом"));

        Set<String> descriptions = new HashSet<>();
        descriptions.add(study.getDescription());
        descriptions.add(work.getDescription());
        descriptions.add(personal.getDescription());
        assertEquals(3, descriptions.size());
    }

    @Test
    @DisplayName("getDescription() переопределён в каждом наследнике")
    void descriptionIsOverriddenInEverySubclass() throws NoSuchMethodException {
        assertEquals(StudyTask.class, StudyTask.class.getMethod("getDescription").getDeclaringClass());
        assertEquals(WorkTask.class, WorkTask.class.getMethod("getDescription").getDeclaringClass());
        assertEquals(PersonalTask.class, PersonalTask.class.getMethod("getDescription").getDeclaringClass());
    }

    @Test
    @DisplayName("повторяемость личной задачи отражается в описании")
    void recurringPersonalTaskIsReflectedInDescription() {
        assertNotEquals(personalTask(true).getDescription(), personalTask(false).getDescription());
    }

    @Test
    @DisplayName("toString() содержит базовые поля")
    void toStringContainsBaseFields() {
        assertBaseFieldsIn(studyTask().toString(), studyTask());
        assertBaseFieldsIn(workTask().toString(), workTask());
        assertBaseFieldsIn(personalTask(true).toString(), personalTask(true));
    }

    @Test
    @DisplayName("equals() и hashCode() учитывают базовые и собственные поля")
    void equalsAndHashCodeAreConsistent() {
        StudyTask first = studyTask();
        StudyTask second = studyTask();

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
        assertNotEquals(first, new StudyTask("Алгебра", "Решить задачи", 3, DUE_DATE,
                TaskStatus.NEW, "Физика", 4));
    }

    @Test
    @DisplayName("equals() возвращает false для null, другого типа и других значений")
    void equalsRejectsDifferentObjects() {
        StudyTask study = studyTask();
        WorkTask work = new WorkTask("Алгебра", "Решить задачи", 3, DUE_DATE,
                TaskStatus.NEW, "Учебный проект", 4);

        assertNotEquals(study, work);
        assertFalse(study.equals(null));
        assertFalse(study.equals("not a task"));
    }

    @Test
    @DisplayName("equals() и hashCode() работают для всех наследников")
    void equalsAndHashCodeForAllSubclasses() {
        assertEquals(workTask(), workTask());
        assertEquals(workTask().hashCode(), workTask().hashCode());
        assertEquals(personalTask(true), personalTask(true));
        assertEquals(personalTask(true).hashCode(), personalTask(true).hashCode());
        assertNotEquals(personalTask(true), personalTask(false));
    }

    @Test
    @DisplayName("все поля классов иерархии private")
    void allFieldsArePrivate() {
        assertAllFieldsPrivate(Task.class);
        assertAllFieldsPrivate(StudyTask.class);
        assertAllFieldsPrivate(WorkTask.class);
        assertAllFieldsPrivate(PersonalTask.class);
    }

    @Test
    @DisplayName("вопрос 4: массив родительского типа и общий результат")
    void parentArrayIsProcessedPolymorphically() {
        Task[] tasks = tasks();
        int total = 0;

        for (Task task : tasks) {
            total += task.getWeight();
            assertFalse(task.getDescription().isBlank());
            assertFalse(task.toString().isBlank());
        }

        assertEquals(60, total);
    }

    @Test
    @DisplayName("вопрос 4: в массиве по два объекта каждого класса")
    void arrayContainsTwoObjectsOfEachSubclass() {
        Task[] tasks = tasks();
        int studies = 0;
        int works = 0;
        int personals = 0;

        for (Task task : tasks) {
            if (task instanceof StudyTask) studies++;
            if (task instanceof WorkTask) works++;
            if (task instanceof PersonalTask) personals++;
        }

        assertEquals(2, studies);
        assertEquals(2, works);
        assertEquals(2, personals);
    }

    private static void assertBaseFieldsIn(String text, Task task) {
        assertTrue(text.contains(task.getTitle()));
        assertTrue(text.contains(task.getDescriptionText()));
        assertTrue(text.contains(String.valueOf(task.getPriority())));
        assertTrue(text.contains(task.getDueDate().toString()));
        assertTrue(text.contains(task.getStatus().name()));
    }

    private static void assertAllFieldsPrivate(Class<?> type) {
        for (Field field : type.getDeclaredFields()) {
            assertTrue(Modifier.isPrivate(field.getModifiers()),
                    type.getSimpleName() + "." + field.getName() + " должно быть private");
        }
    }

    private static Task[] tasks() {
        return new Task[]{
                new StudyTask("Учебная 1", "Первая учебная", 3, DUE_DATE,
                        TaskStatus.NEW, "Математика", 4),
                new StudyTask("Учебная 2", "Вторая учебная", 1, DUE_DATE.plusDays(1),
                        TaskStatus.IN_PROGRESS, "Физика", 2),
                new WorkTask("Рабочая 1", "Первая рабочая", 2, DUE_DATE.plusDays(2),
                        TaskStatus.DONE, "Проект A", 5),
                new WorkTask("Рабочая 2", "Вторая рабочая", 5, DUE_DATE.plusDays(3),
                        TaskStatus.NEW, "Проект B", 1),
                new PersonalTask("Личная 1", "Первая личная", 4, DUE_DATE.plusDays(4),
                        TaskStatus.DONE, "Дом", true),
                new PersonalTask("Личная 2", "Вторая личная", 4, DUE_DATE.plusDays(5),
                        TaskStatus.IN_PROGRESS, "Спорт", false)
        };
    }

    private static StudyTask studyTask() {
        return new StudyTask("Алгебра", "Решить задачи", 3, DUE_DATE,
                TaskStatus.NEW, "Математика", 4);
    }

    private static WorkTask workTask() {
        return new WorkTask("Отчёт", "Подготовить отчёт", 2, DUE_DATE.plusDays(2),
                TaskStatus.IN_PROGRESS, "Учебный проект", 5);
    }

    private static PersonalTask personalTask(boolean recurring) {
        return new PersonalTask("Уборка", "Убраться дома", 4, DUE_DATE.plusDays(3),
                TaskStatus.DONE, "Дом", recurring);
    }
}
