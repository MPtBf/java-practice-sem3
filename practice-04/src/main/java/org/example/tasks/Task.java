package org.example.tasks;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Базовая задача.
 *
 * <p>Поле «описание» доступно через {@link #getDescriptionText()}, а общий
 * полиморфный метод {@link #getDescription()} должен возвращать полное описание
 * задачи, включая параметры наследника.</p>
 */
public abstract class Task {

    private String title;
    private String description;
    private int priority;
    private LocalDate dueDate;
    private TaskStatus status;

    /**
     * @param status статус задачи: {@link TaskStatus#NEW}, {@link TaskStatus#IN_PROGRESS}
     *               или {@link TaskStatus#DONE}
     * @throws IllegalArgumentException если параметры задачи некорректны
     */
    protected Task(String title,
                   String description,
                   int priority,
                   LocalDate dueDate,
                   TaskStatus status) {
        validateTitle(title);
        validateDescription(description);
        validatePriority(priority);
        validateDueDate(dueDate);
        validateStatus(status);

        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    /**
     * @return исходный текст описания задачи
     */
    public String getDescriptionText() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    /**
     * Общий полиморфный метод. В наследниках должен добавлять собственные параметры.
     */
    public abstract String getDescription();

    /**
     * Вес задачи для расчёта общего результата.
     */
    public abstract int getWeight();

    /**
     * Общая часть описания, которую наследники дополняют своими полями.
     */
    protected String getBaseDescription() {
        return "название: " + title
                + ", описание: " + description
                + ", приоритет: " + priority
                + ", срок: " + dueDate
                + ", статус: " + status;
    }

    private static void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("название задачи не должно быть пустым");
        }
    }

    private static void validateDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("описание задачи не должно быть пустым");
        }
    }

    private static void validatePriority(int priority) {
        if (priority < 1 || priority > 5) {
            throw new IllegalArgumentException("приоритет должен быть от 1 до 5");
        }
    }

    private static void validateDueDate(LocalDate dueDate) {
        if (dueDate == null) {
            throw new IllegalArgumentException("срок задачи не должен быть пустым");
        }
    }

    private static void validateStatus(TaskStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("статус задачи не должен быть пустым");
        }
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", dueDate=" + dueDate +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Task task = (Task) object;
        return priority == task.priority
                && Objects.equals(title, task.title)
                && Objects.equals(description, task.description)
                && Objects.equals(dueDate, task.dueDate)
                && Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, priority, dueDate, status);
    }
}
