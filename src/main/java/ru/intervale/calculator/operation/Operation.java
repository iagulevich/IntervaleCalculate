package ru.intervale.calculator.operation;

import java.util.Arrays;

public enum Operation {

    PLUS('+', 1),
    MINUS('-', 1),
    MUL('*', 2),
    DIV('/', 2),
    PER('%', 2),
    POW('^', 3);

    public static final int DEFAULT_PRIORITY = 1;
    private final char symbol;
    private final int priority;

    Operation(char symbol, int priority) {
        this.symbol = symbol;
        this.priority = priority;
    }

    public static boolean isOperation(char symbol) {
        return Arrays.stream(values()).anyMatch(operation -> operation.getSymbol() == symbol);
    }
    public static int getPriority(char symbol) {
        return Arrays.stream(values()).filter(operation -> operation.symbol == symbol)
                .findFirst().map(Operation::getPriority).orElse(DEFAULT_PRIORITY);
    }

    public char getSymbol() {
        return symbol;
    }

    public int getPriority() {
        return priority;
    }
}
