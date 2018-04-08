package ru.intervale.calculator.operation;

import ru.intervale.calculator.CalculatorException;

public class OperationUnrealized extends CalculatorException {

    public OperationUnrealized() {
        this("Operation unrealized");
    }

    private OperationUnrealized(String message) {
        super(message);
    }
}
