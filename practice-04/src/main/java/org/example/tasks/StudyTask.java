package org.example.tasks;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Учебная задача. Вес: {@code priority * plannedHours}.
 */
public class StudyTask extends Task {

    private String subject;
    private int plannedHours;

    /**
     * @throws IllegalArgumentException если предмет пустой или часы меньше 1
     */
    public StudyTask(String title,
                     String description,
                     int priority,
                     LocalDate dueDate,
                     TaskStatus status,
                     String subject,
                     int plannedHours) {
        super(title, description, priority, dueDate, status);

        if (subject == null || subject.isBlank()) {
            throw new IllegalArgumentException("предмет не должен быть пустым");
        }
        if (plannedHours < 1) {
            throw new IllegalArgumentException("количество часов должно быть >= 1");
        }

        this.subject = subject;
        this.plannedHours = plannedHours;
    }

    public String getSubject() {
        return subject;
    }

    public int getPlannedHours() {
        return plannedHours;
    }

    @Override
    public String getDescription() {
        return getBaseDescription()
                + ", предмет: " + subject
                + ", запланированные часы: " + plannedHours;
    }

    @Override
    public int getWeight() {
        return getPriority() * plannedHours;
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
        StudyTask studyTask = (StudyTask) object;
        return plannedHours == studyTask.plannedHours
                && Objects.equals(subject, studyTask.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subject, plannedHours);
    }
}
