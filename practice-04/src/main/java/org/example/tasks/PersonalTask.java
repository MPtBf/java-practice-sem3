package org.example.tasks;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Личная задача. Вес: {@code priority * (recurring ? 3 : 1)}.
 */
public class PersonalTask extends Task {

    private String category;
    private boolean recurring;

    /**
     * @throws IllegalArgumentException если категория пустая
     */
    public PersonalTask(String title,
                        String description,
                        int priority,
                        LocalDate dueDate,
                        TaskStatus status,
                        String category,
                        boolean recurring) {
        super(title, description, priority, dueDate, status);

        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("категория не должна быть пустой");
        }

        this.category = category;
        this.recurring = recurring;
    }

    public String getCategory() {
        return category;
    }

    public boolean isRecurring() {
        return recurring;
    }

    @Override
    public String getDescription() {
        return getBaseDescription()
                + ", категория: " + category
                + ", повторяющаяся: " + (recurring ? "да" : "нет");
    }

    @Override
    public int getWeight() {
        return getPriority() * (recurring ? 3 : 1);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        if (!super.equals(object)) {
            return false;
        }
        PersonalTask personalTask = (PersonalTask) object;
        return recurring == personalTask.recurring
                && Objects.equals(category, personalTask.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), category, recurring);
    }
}
