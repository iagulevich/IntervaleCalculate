package ru.intervale.calculator;

import ru.intervale.calculator.dto.Result;

import java.util.List;

public interface MultiCalculator extends Calculator {

    List<Result> calculate(List<String> expressions);
}
