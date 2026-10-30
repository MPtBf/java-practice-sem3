# Практика 04

## Задачи

[task.md](task.md)

## Запуск тестов

В IntelliJ IDEA: правой кнопкой по папке `src/test/java` → **Run 'All Tests'**.

Либо через Maven:

```bash
mvn test
```

## Структура

```text
src/main/java/org/example/
├── Main.java
├── calculations/
│   ├── Calculation.java
│   ├── DistanceCalculation.java
│   └── SeriesSumCalculation.java
├── intlist/
│   └── IntList.java
└── tasks/
    ├── PersonalTask.java
    ├── StudyTask.java
    ├── Task.java
    ├── TaskStatus.java
    └── WorkTask.java

src/test/java/org/example/
├── calculations/
│   └── Question3Test.java
├── intlist/
│   └── Question1Test.java
└── tasks/
    └── Question2And4Test.java
```
