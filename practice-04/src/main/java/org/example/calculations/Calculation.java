package org.example.calculations;

import java.util.Objects;

/**
 * Базовый класс для расчётов из предыдущих практических работ.
 *
 * <p>Общий полиморфный метод — {@link #getResultDescription()}.</p>
 */
public abstract class Calculation {

    private final String name;

    /**
     * @param name название расчёта, не должно быть пустым
     * @throws IllegalArgumentException если имя {@code null} или состоит из пробелов
     */
    protected Calculation(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("название расчёта не должно быть пустым");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract double calculate();

    /**
     * Общий полиморфный метод: текст с названием, параметрами и результатом.
     */
    public abstract String getResultDescription();

    @Override
    public String toString() {
        return "Calculation{name='" + name + "'}";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Calculation calculation = (Calculation) object;
        return Objects.equals(name, calculation.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
