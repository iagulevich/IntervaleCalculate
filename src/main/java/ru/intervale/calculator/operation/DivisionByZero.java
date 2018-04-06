package ru.intervale.calculator.operation;

import ru.intervale.calculator.CalculatorException;

public class DivisionByZero extends CalculatorException {

    public DivisionByZero() {
        this("Division by zero");
    }

    private DivisionByZero(String message) {
        super(message);
    }
}
