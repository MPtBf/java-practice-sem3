package org.example.calculations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Вопрос 3 — иерархия Calculation")
class Question3Test {

    @Test
    @DisplayName("имя расчёта доступно через getName()")
    void nameIsAvailable() {
        assertEquals("Тестовый расчёт", calculationWithName("Тестовый расчёт").getName());
    }

    @Test
    @DisplayName("пустое или null-имя недопустимо")
    void blankNameIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> calculationWithName(null));
        assertThrows(IllegalArgumentException.class, () -> calculationWithName(""));
        assertThrows(IllegalArgumentException.class, () -> calculationWithName("   "));
    }

    @Test
    @DisplayName("toString() содержит имя расчёта")
    void toStringContainsName() {
        assertTrue(calculationWithName("Тестовый расчёт").toString().contains("Тестовый расчёт"));
    }

    @Test
    @DisplayName("equals() и hashCode() базового класса согласованы")
    void baseEqualsAndHashCodeAreConsistent() {
        Calculation first = calculationWithName("Одинаковое имя");
        Calculation second = calculationWithName("Одинаковое имя");

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
        assertFalse(first.equals(null));
        assertFalse(first.equals("not a calculation"));
    }

    @Test
    @DisplayName("общий метод getResultDescription() работает полиморфно")
    void resultDescriptionIsPolymorphic() {
        Calculation[] calculations = {
                new SeriesSumCalculation(3),
                new DistanceCalculation(0, 0, 3, 4)
        };

        Set<String> descriptions = new HashSet<>();
        for (Calculation calculation : calculations) {
            String description = calculation.getResultDescription();
            assertFalse(description.isBlank());
            assertTrue(description.contains(calculation.getName()));
            descriptions.add(description);
        }

        assertEquals(2, descriptions.size());
    }

    @Test
    @DisplayName("во всей иерархии поля private")
    void allFieldsArePrivate() {
        assertAllFieldsPrivate(Calculation.class);
        assertAllFieldsPrivate(SeriesSumCalculation.class);
        assertAllFieldsPrivate(DistanceCalculation.class);
    }

    @Test
    @DisplayName("сумма ряда: n = 1")
    void sumForOne() {
        assertEquals(1.0, new SeriesSumCalculation(1).calculate(), 1e-9);
    }

    @Test
    @DisplayName("сумма ряда: 1 + 1/2 + 1/3")
    void sumForThree() {
        assertEquals(1.0 + 1.0 / 2 + 1.0 / 3,
                new SeriesSumCalculation(3).calculate(), 1e-9);
    }

    @Test
    @DisplayName("сумма ряда: n < 1 недопустим")
    void invalidSeriesNIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new SeriesSumCalculation(0));
        assertThrows(IllegalArgumentException.class, () -> new SeriesSumCalculation(-1));
    }

    @Test
    @DisplayName("сумма ряда: параметр, описание, toString")
    void seriesParameterAndText() {
        SeriesSumCalculation calculation = new SeriesSumCalculation(3);

        assertEquals(3, calculation.getN());
        assertTrue(calculation.getResultDescription().contains("3"));
        assertTrue(calculation.toString().contains("3"));
    }

    @Test
    @DisplayName("сумма ряда: equals() и hashCode() учитывают n")
    void seriesEqualsAndHashCode() {
        SeriesSumCalculation first = new SeriesSumCalculation(3);
        SeriesSumCalculation second = new SeriesSumCalculation(3);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
        assertNotEquals(first, new SeriesSumCalculation(4));
    }

    @Test
    @DisplayName("расстояние между (0,0) и (3,4) равно 5")
    void distanceIsCalculated() {
        assertEquals(5.0, new DistanceCalculation(0, 0, 3, 4).calculate(), 1e-9);
    }

    @Test
    @DisplayName("расстояние от точки до неё самой равно 0")
    void distanceToSamePointIsZero() {
        assertEquals(0.0, new DistanceCalculation(1, 1, 1, 1).calculate(), 1e-9);
    }

    @Test
    @DisplayName("расстояние для отрицательных координат")
    void distanceWithNegativeCoordinates() {
        assertEquals(5.0, new DistanceCalculation(-1, -1, 2, 3).calculate(), 1e-9);
    }

    @Test
    @DisplayName("расстояние: неконечные координаты недопустимы")
    void nonFiniteCoordinatesAreRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new DistanceCalculation(Double.NaN, 0, 1, 1));
        assertThrows(IllegalArgumentException.class,
                () -> new DistanceCalculation(0, Double.POSITIVE_INFINITY, 1, 1));
    }

    @Test
    @DisplayName("расстояние: координаты, описание, toString")
    void distanceParametersAndText() {
        DistanceCalculation calculation = new DistanceCalculation(1, 2, 3, 4);

        assertEquals(1.0, calculation.getX1(), 1e-9);
        assertEquals(2.0, calculation.getY1(), 1e-9);
        assertEquals(3.0, calculation.getX2(), 1e-9);
        assertEquals(4.0, calculation.getY2(), 1e-9);
        assertTrue(calculation.getResultDescription().contains("3"));
        assertTrue(calculation.toString().contains("4"));
    }

    @Test
    @DisplayName("расстояние: equals() и hashCode() учитывают координаты")
    void distanceEqualsAndHashCode() {
        DistanceCalculation first = new DistanceCalculation(1, 2, 3, 4);
        DistanceCalculation second = new DistanceCalculation(1, 2, 3, 4);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
        assertNotEquals(first, new DistanceCalculation(1, 2, 3, 5));
    }

    private static Calculation calculationWithName(String name) {
        return new Calculation(name) {
            @Override
            public double calculate() {
                return 0;
            }

            @Override
            public String getResultDescription() {
                return "тестовое описание";
            }
        };
    }

    private static void assertAllFieldsPrivate(Class<?> type) {
        for (Field field : type.getDeclaredFields()) {
            assertTrue(Modifier.isPrivate(field.getModifiers()),
                    type.getSimpleName() + "." + field.getName() + " должно быть private");
        }
    }
}
