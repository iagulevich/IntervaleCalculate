package ru.intervale.calculator.operation;

import ru.intervale.calculator.exceptions.CalculatorException;

import java.util.Arrays;

public enum Operation {

    PLUS('+', 1, (t1, t2) -> t2 + t1),
    MINUS('-', 1, (t1, t2) -> t2 - t1),
    MUL('*', 2, (t1, t2) -> t2 * t1),
    DIV('/', 2, Operation::div),
    PER('%', 2, (t1, t2) -> t1 % t2),
    POW('^', 3, (t1, t2) -> Math.pow(t2, t1));

    public static final int DEFAULT_PRIORITY = 1;
    private final char symbol;
    private final int priority;
    private final OperationFunction<Double> function;

    Operation(char symbol, int priority, OperationFunction<Double> function) {
        this.symbol = symbol;
        this.priority = priority;
        this.function = function;
    }

    public static Operation find(char symbol) {
        return Arrays.stream(values()).filter(operation -> operation.symbol == symbol).findFirst()
                .orElseThrow(() -> new CalculatorException("Operation not found for: " + symbol));
    }

    public static boolean isOperation(char symbol) {
        return Arrays.stream(values()).anyMatch(operation -> operation.getSymbol() == symbol);
    }

    public static int getPriority(char symbol) {
        return Arrays.stream(values()).filter(operation -> operation.symbol == symbol)
                .findFirst().map(Operation::getPriority).orElse(DEFAULT_PRIORITY);
    }

    private static Double div(Double t1, Double t2) {
        if (t1 == 0) {
            throw new CalculatorException("Divided to zero.");
        } else {
            return t2 / t1;
        }
    }

    public static boolean isUnary(char symbol) {
        return symbol == MINUS.symbol || symbol == PLUS.symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getPriority() {
        return priority;
    }

    public Double perform(Double d1, Double d2) {
        return function.perform(d1, d2);
    }
}
