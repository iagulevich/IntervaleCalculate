package ru.intervale.calculator.operation;

import java.util.Arrays;
import java.util.Stack;

public enum Operation {

    RIGHT_BRACKET(')', 1, Operation::unrealized),
    LEFT_BRACKET('(', 1, Operation::unrealized),
    PLUS('+', 2, (t1, t2) -> t1 + t2),
    MINUS('-', 2, (t1, t2) -> t1 - t2),
    MUL('*', 3, (t1, t2) -> t1 * t2),
    DIV('/', 3, Operation::div),
    PER('%', 3, (t1, t2) -> (t1 * t2) / 100),
    POW('^', 4, Math::pow);

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
                .orElseThrow(() -> new OperationNotFound(symbol));
    }

    public static boolean isOperation(char symbol) {
        return Arrays.stream(values()).anyMatch(operation -> operation.getSymbol() == symbol);
    }

    public static boolean isOperation(String token) {
        return token.length() == 1 && isOperation(token.charAt(0));
    }

    public static int getPriority(char symbol) {
        return Arrays.stream(values()).filter(operation -> operation.symbol == symbol)
                .findFirst().map(Operation::getPriority).orElse(DEFAULT_PRIORITY);
    }

    public static int getPriority(Stack<Character> stack) {
        return stack.isEmpty() ? DEFAULT_PRIORITY : getPriority(stack.peek());
    }

    private static Double div(Double t1, Double t2) {
        if (t2 == 0) {
            throw new DivisionByZero();
        } else {
            return t1 / t2;
        }
    }

    public static boolean isUnary(char symbol) {
        return symbol == MINUS.symbol || symbol == PLUS.symbol;
    }

    public static boolean isBracket(char symbol) {
        return isLeftBracket(symbol) || isRightBracket(symbol);
    }

    public static boolean isLeftBracket(char symbol) {
        return LEFT_BRACKET.symbol == symbol;
    }

    public static boolean isRightBracket(char symbol) {
        return RIGHT_BRACKET.symbol == symbol;
    }

    private static Double unrealized(Double t1, Double t2) {
        throw new OperationUnrealized();
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
