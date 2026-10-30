package org.example.tasks;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Рабочая задача. Вес: {@code priority * estimatedHours * 2}.
 */
public class WorkTask extends Task {

    private String project;
    private int estimatedHours;

    /**
     * @throws IllegalArgumentException если проект пустой или часы меньше 1
     */
    public WorkTask(String title,
                    String description,
                    int priority,
                    LocalDate dueDate,
                    TaskStatus status,
                    String project,
                    int estimatedHours) {
        super(title, description, priority, dueDate, status);

        if (project == null || project.isBlank()) {
            throw new IllegalArgumentException("проект не должен быть пустым");
        }
        if (estimatedHours < 1) {
            throw new IllegalArgumentException("количество часов должно быть >= 1");
        }

        this.project = project;
        this.estimatedHours = estimatedHours;
    }

    public String getProject() {
        return project;
    }

    public int getEstimatedHours() {
        return estimatedHours;
    }

    @Override
    public String getDescription() {
        return getBaseDescription()
                + ", проект: " + project
                + ", оценка часов: " + estimatedHours;
    }

    @Override
    public int getWeight() {
        return getPriority() * estimatedHours * 2;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) { // link
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        if (!super.equals(object)) { // parent's fields
            return false;
        }
        // already checked that classes are the same, now making compiler aware of its type, just to access fields
        WorkTask workTask = (WorkTask) object;

        return estimatedHours == workTask.estimatedHours
                && Objects.equals(project, workTask.project); // own new fields
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), project, estimatedHours);
    }
}
