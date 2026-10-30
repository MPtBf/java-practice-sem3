package org.example.calculations;

import java.util.Objects;

/**
 * Расстояние между двумя точками (задача 4 из практики 02).
 */
public class DistanceCalculation extends Calculation {

    private final double x1;
    private final double y1;
    private final double x2;
    private final double y2;

    /**
     * @throws IllegalArgumentException если хотя бы одна координата не является
     *                                  конечным числом
     */
    public DistanceCalculation(double x1, double y1, double x2, double y2) {
        super("Расстояние между точками");

        validateCoordinate(x1, "x1");
        validateCoordinate(y1, "y1");
        validateCoordinate(x2, "x2");
        validateCoordinate(y2, "y2");

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public double getX1() {
        return x1;
    }

    public double getY1() {
        return y1;
    }

    public double getX2() {
        return x2;
    }

    public double getY2() {
        return y2;
    }

    @Override
    public double calculate() {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    @Override
    public String getResultDescription() {
        return getName()
                + ": (" + x1 + "; " + y1 + ") -> (" + x2 + "; " + y2 + ")"
                + ", расстояние = " + calculate();
    }

    @Override
    public String toString() {
        return "DistanceCalculation{name='" + getName() + "'"
                + ", x1=" + x1
                + ", y1=" + y1
                + ", x2=" + x2
                + ", y2=" + y2
                + "}";
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
        DistanceCalculation calculation = (DistanceCalculation) object;
        return Double.compare(x1, calculation.x1) == 0
                && Double.compare(y1, calculation.y1) == 0
                && Double.compare(x2, calculation.x2) == 0
                && Double.compare(y2, calculation.y2) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), x1, y1, x2, y2);
    }

    private static void validateCoordinate(double value, String name) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("координата " + name + " должна быть конечным числом");
        }
    }
}
