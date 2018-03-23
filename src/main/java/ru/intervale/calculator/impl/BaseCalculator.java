package ru.intervale.calculator.impl;

import ru.intervale.calculator.MultiCalculator;
import ru.intervale.calculator.dto.Result;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseCalculator implements MultiCalculator {

    @Override
    public List<Result> calculate(List<String> expressions) {
        return expressions.stream().map(this::calculate).collect(Collectors.toList());
    }

    protected boolean isOperation(char c) {
        switch (c) {
            case '-':
            case '+':
            case '*':
            case '/':
            case '^':
            case '%':
                return true;
        }
        return false;
    }

    protected byte operationPrior(char op) {
        switch (op) {
            case '^':
                return 3;
            case '*':
            case '/':
            case '%':
                return 2;
        }
        return 1;
    }
}
