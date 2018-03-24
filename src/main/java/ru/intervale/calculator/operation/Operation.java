package ru.intervale.calculator.operation;

import java.util.Arrays;

public enum Operation {

    PLUS('+', 1),
    MINUS('-', 1),
    MUL('*', 2),
    DIV('/', 2),
    PER('%', 2),
    POW('^', 3),
    NULL(' ',0);


    private final char symbol;
    private final int priority;

    Operation(char symbol, int priority) {
        this.symbol = symbol;
        this.priority = priority;
    }

    public static boolean isOperation(char symbol) {
        return Arrays.stream(values()).anyMatch(op -> op.getSymbol() == symbol);
    }
    public static int getPriority(char symbol) {
        Operation found = NULL;
        for (Operation operation : values()) {
            if (operation.symbol == symbol){
                found = operation;
                break;
            }
        }
        return found.getPriority();
    }

    public char getSymbol() {
        return symbol;
    }

    public int getPriority() {
        return priority;
    }
}
