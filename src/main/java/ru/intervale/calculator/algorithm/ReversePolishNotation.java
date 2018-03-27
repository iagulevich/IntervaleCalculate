package ru.intervale.calculator.algorithm;

import ru.intervale.calculator.operation.Operation;

public class ReversePolishNotation {

    private final char[] input;

    public ReversePolishNotation(String input) {
        this.input = input.toCharArray();
    }

    public String rpn() {

        char operation = 0;
        StringBuilder result = new StringBuilder();
        boolean startStr = true;

        for (char symbol : input) {
            if (Character.isDigit(symbol)) {
                startStr = false;
                result.append(symbol);
            } else if (Operation.isOperation(symbol)) {
                if ((Operation.isUnary(symbol) && operation != 0) || (startStr)) {
                    result.append(symbol);
                    continue;
                }
                result.append(" ");
                operation = symbol;
            }
        }
        result.append(" ").append(operation);
        return result.toString();
    }

}
