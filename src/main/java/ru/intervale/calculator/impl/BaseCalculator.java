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

    protected int operationPrior(char symbol) {
        return Operation.getPriority(symbol);
    }

    protected String rpn(String input) {

        StringBuilder strStack = new StringBuilder();
        StringBuilder strOut = new StringBuilder();
        boolean operation = false;
        boolean startStr = true;


        for (char symbol: input.toCharArray()) {

            if (isOperation(symbol)) {
                if (((operation && symbol == '-' && strStack.length() > 0) || (startStr)) ||
                        (operation && symbol == '+' && strStack.length() > 0) || (startStr)) {
                    strOut.append(" ").append(symbol);
                    continue;
                }
                operation = true;

                strOut.append(" ");
                strStack.append(symbol);
            } else {
                if (Character.isDigit(symbol)) {
                    operation = false;
                    startStr = false;
                }
                strOut.append(symbol);
            }
        }
        if (strStack.length() > 0) {
            strOut.append(" ").append(strStack.charAt(strStack.length() - 1));
            strStack.substring(strStack.length() - 1);
        }
        return strOut.toString();
    }

}
