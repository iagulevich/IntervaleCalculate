package ru.intervale.calculator.operation;

import ru.intervale.calculator.exceptions.CalculatorException;

import java.util.Arrays;
import java.util.Stack;

public enum Operation {

    RIGHT_BRACKET(')', 1, (t1, t2) -> t1),
    LEFT_BRACKET('(', 1, (t1, t2) -> t1),
    PLUS('+', 2, (t1, t2) -> t2 + t1),
    MINUS('-', 2, (t1, t2) -> t2 - t1),
    MUL('*', 3, (t1, t2) -> t2 * t1),
    DIV('/', 3, Operation::div),
    PER('%', 3, (t1, t2) -> t1 % t2),
    POW('^', 4, (t1, t2) -> Math.pow(t2, t1));

    public static final int DEFAULT_PRIORITY = 0;
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

    public static int getPriority(Stack<Character> stack){
        return stack.isEmpty() ? DEFAULT_PRIORITY : getPriority(stack.peek());
    }

    private static Double div(Double t1, Double t2) {
        if (t1 == 0) {
            throw new CalculatorException("Division by zero");
        } else {
            return t2 / t1;
        }
    }

    public static boolean isUnary(char symbol) {
        return symbol == MINUS.symbol || symbol == PLUS.symbol;
    }

    public static boolean isLeftBracket(char symbol) {
        return LEFT_BRACKET.symbol == symbol;
    }

    public static boolean isRightBracket(char symbol) {
        return RIGHT_BRACKET.symbol == symbol;
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
