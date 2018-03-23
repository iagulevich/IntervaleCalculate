package ru.intervale.calculator.task1;

import ru.intervale.calculator.dto.Result;

import java.util.Stack;
import java.util.StringTokenizer;

public class IntervaleCalculator1 {

    private String revPolNotation(String stringIn) {

        String strStack = "";
        String strOut = "";
        char charIn;
        boolean operation = false;
        boolean startStr = true;

        for (int i = 0; i < stringIn.length(); i++) {
            charIn = stringIn.charAt(i);

            if (isOperation(charIn)) {
                //проверка на унарный минус и унарный плюс
                if (((operation && charIn == '-' && strStack.length() > 0) || (startStr)) ||
                        (operation && charIn == '+' && strStack.length() > 0) || (startStr)) {
                    strOut += " " + charIn;
                    continue;
                }
                operation = true;

                strOut += " ";
                strStack += charIn;
            } else {
                if (Character.isDigit(charIn)) {
                    operation = false;
                    startStr = false;
                }
                strOut += charIn;
            }
        }
        if (strStack.length() > 0) {
            strOut += " " + strStack.charAt(strStack.length() - 1);
            strStack.substring(strStack.length() - 1);
        }
        return strOut;
    }

    private static boolean isOperation(char c) {
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

    public Result calculate(String input) {

        if (input.isEmpty()) {
            return new Result(input, "Строка пуста!");
        }
        String sIn = revPolNotation(input);
        int forPercent = 100;
        double d1 = 0, d2 = 0;
        String strTmp;
        Stack<Double> stack = new Stack<>();
        StringTokenizer strToken = new StringTokenizer(sIn);

        while (strToken.hasMoreTokens()) {
            try {
                strTmp = strToken.nextToken().trim();
                if (1 == strTmp.length() && isOperation(strTmp.charAt(0))) {
                    if (stack.size() < 2) {
                        return new Result(input, "Неверное количество данных в стеке для операции " + strTmp);
                    }

                    d1 = stack.pop();
                    d2 = stack.pop();

                    switch (strTmp.charAt(0)) {
                        case '+':
                            d1 += d2;
                            break;
                        case '-':
                            d1 = d2 - d1;
                            break;
                        case '/':
                            d1 = d2 / d1;
                            break;
                        case '*':
                            d1 *= d2;
                            break;
                        case '%':
                            d1 *= d2 / forPercent;
                            break;
                        case '^':
                            d1 = Math.pow(d2, d1);
                            break;
                    }
                    stack.push(d1);
                } else {
                    d1 = Double.parseDouble(strTmp);
                    stack.push(d1);
                }

            } catch (Exception e) {
                return new Result(input, "Недопустимый символ в выражении!");
            }
        }

        if (stack.size() > 1) {
            return new Result(input, "Количество операторов не соответствует количеству операндов!");
        }
        return new Result(input, stack.pop());
    }

}

