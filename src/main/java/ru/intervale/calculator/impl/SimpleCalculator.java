package ru.intervale.calculator.impl;

import ru.intervale.calculator.dto.Result;

import java.util.Stack;
import java.util.StringTokenizer;

public class SimpleCalculator extends BaseCalculator {

    @Override
    public Result calculate(String expression) {

        if (expression.isEmpty()) {
            return new Result(expression, "Строка пуста!");
        }
        String rpn = rpn(expression);

        double d1 = 0, d2 = 0;
        String token;
        Stack<Double> stack = new Stack<>();
        StringTokenizer tokenizer = new StringTokenizer(rpn);

        while (tokenizer.hasMoreTokens()) {
            try {
                token = tokenizer.nextToken().trim();
                if (1 == token.length() && isOperation(token.charAt(0))) {
                    if (stack.size() < 2) {
                        return new Result(expression, "Неверное количество данных в стеке для операции " + token);
                    }

                    d1 = stack.pop();
                    d2 = stack.pop();

                    switch (token.charAt(0)) {
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
                    d1 = Double.parseDouble(token);
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

}
