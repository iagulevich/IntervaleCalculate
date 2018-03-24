package ru.intervale.calculator.impl;

import ru.intervale.calculator.dto.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class SimpleCalculator extends BaseCalculator {

    @Override
    public Result calculate(String expression) {

        if (expression.isEmpty()) {
            return new Result(expression, "Строка пуста!");
        }
        String sIn = revPolNotation(expression);

        double d1 = 0, d2 = 0;
        String strTmp;
        Stack<Double> stack = new Stack<>();
        StringTokenizer strToken = new StringTokenizer(sIn);

        while (strToken.hasMoreTokens()) {
            try {
                strTmp = strToken.nextToken().trim();
                if (1 == strTmp.length() && isOperation(strTmp.charAt(0))) {
                    if (stack.size() < 2) {
                        return new Result(expression, "Неверное количество данных в стеке для операции " + strTmp);
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
                            d1 *= d2 / 100;
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
                return new Result(expression, "Недопустимый символ в выражении!");
            }
        }

        if (stack.size() > 1) {
            return new Result(expression, "Количество операторов не соответствует количеству операндов!");
        }
        return new Result(expression, stack.pop());
    }

    @Override
    public List<Result> calculate(List<String> expressions) {
        final List<Result> results = new ArrayList<>(expressions.size());
        expressions.forEach(s -> results.add(calculate(s)));
        return results;
    }

    private String revPolNotation(String stringIn) {

        String strStack = "";
        StringBuilder strOut = new StringBuilder();
        char charIn;
        boolean operation = false;
        boolean startStr = true;

        for (int i = 0; i < stringIn.length(); i++) {
            charIn = stringIn.charAt(i);

            if (isOperation(charIn)) {
                //проверка на унарный минус и унарный плюс
                if (((operation && charIn == '-' && strStack.length() > 0) || (startStr)) ||
                        (operation && charIn == '+' && strStack.length() > 0) || (startStr)) {
                    strOut.append(" ").append(charIn);
                    continue;
                }
                operation = true;

                strOut.append(" ");
                strStack += charIn;
            } else {
                if (Character.isDigit(charIn)) {
                    operation = false;
                    startStr = false;
                }
                strOut.append(charIn);
            }
        }
        if (strStack.length() > 0) {
            strOut.append(" ").append(strStack.charAt(strStack.length() - 1));
            strStack.substring(strStack.length() - 1);
        }
        return strOut.toString();
    }


}
