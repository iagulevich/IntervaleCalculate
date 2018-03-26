package ru.intervale.calculator.algorithm;

import ru.intervale.calculator.operation.Operation;

public class ReversePolishNotation {

    private final char[] input;

    public ReversePolishNotation(String input) {
        this.input = input.toCharArray();
    }

    public String rpn() {

        StringBuilder strStack = new StringBuilder();
        StringBuilder result = new StringBuilder();
        boolean operation = false;
        boolean startStr = true;

        for (char symbol: input) {
            if (Operation.isOperation(symbol)) {
                if (((operation && symbol == '-' && strStack.length() > 0) || (startStr)) ||
                        (operation && symbol == '+' && strStack.length() > 0) || (startStr)) {
                    result.append(" ").append(symbol);
                    continue;
                }
                operation = true;

                result.append(" ");
                strStack.append(symbol);
            } else {
                if (Character.isDigit(symbol)) {
                    operation = false;
                    startStr = false;
                }
                result.append(symbol);
            }
        }
        if (strStack.length() > 0) {
            result.append(" ").append(strStack.charAt(strStack.length() - 1));
            strStack.substring(strStack.length() - 1);
        }
        return result.toString();
    }
}
