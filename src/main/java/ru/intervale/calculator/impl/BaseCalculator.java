package ru.intervale.calculator.impl;

import ru.intervale.calculator.MultiCalculator;
import ru.intervale.calculator.dto.Result;
import ru.intervale.calculator.operation.Operation;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseCalculator implements MultiCalculator {

    @Override
    public List<Result> calculate(List<String> expressions) {
        return expressions.stream().map(this::calculate).collect(Collectors.toList());
    }

    protected boolean isOperation(char symbol) {
        return Operation.isOperation(symbol);
    }

    protected boolean isOperation(String token) {
        return Operation.isOperation(token);
    }

    protected int operationPrior(char symbol) {
        return Operation.getPriority(symbol);
    }

}
