package org.example.calculations;

import java.util.Objects;

/**
 * Сумма ряда {@code 1 + 1/2 + 1/3 + ... + 1/n} (задача 1 из практики 02).
 */
public class SeriesSumCalculation extends Calculation {

    private final int n;

    /**
     * @throws IllegalArgumentException если {@code n < 1}
     */
    public SeriesSumCalculation(int n) {
        super("Сумма ряда");

        if (n < 1) {
            throw new IllegalArgumentException("n должно быть >= 1");
        }

        this.n = n;
    }

    public int getN() {
        return n;
    }

    @Override
    public double calculate() {
        double sum = 0.0;
        for (int i = 1; i <= n; i++) {
            sum += 1.0 / i;
        }
        return sum;
    }

    @Override
    public String getResultDescription() {
        return getName() + ": n = " + n + ", сумма = " + calculate();
    }

    @Override
    public String toString() {
        return "SeriesSumCalculation{name='" + getName() + "', n=" + n + "}";
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
        SeriesSumCalculation calculation = (SeriesSumCalculation) object;
        return n == calculation.n;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), n);
    }
}
