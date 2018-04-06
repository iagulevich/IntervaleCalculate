package ru.intervale.calculator.operation;

import ru.intervale.calculator.CalculatorException;

public class OperationNotFound extends CalculatorException {

    public OperationNotFound(char symbol) {
        this("Operation not found for: " + symbol);
    }

    private OperationNotFound(String message) {
        super(message);
    }
}
