package ru.intervale.calculator.impl;

import ru.intervale.calculator.algorithm.ReversePolishNotation;
import ru.intervale.calculator.dto.Result;
import ru.intervale.calculator.operation.Operation;

import java.util.Stack;
import java.util.StringTokenizer;

public class SimpleCalculator extends BaseCalculator {

    @Override
    public Result calculate(String expression) {

        if (expression.isEmpty()) {
            return new Result(expression, "Строка пуста!");
        }
        String rpn = new ReversePolishNotation(expression).rpn();

        double d1, d2;
        String token;

        Stack<Double> stack = new Stack<>();
        StringTokenizer tokenizer = new StringTokenizer(rpn);

        char symbol;
        while (tokenizer.hasMoreTokens()) {
            try {
                token = tokenizer.nextToken().trim();
                symbol = token.charAt(0);
                if (1 == token.length() && isOperation(symbol)) {
                    if (stack.size() < 2) {
                        return new Result(expression, "Неверное количество данных в стеке для операции " + token);
                    }

                    d1 = stack.pop();
                    d2 = stack.pop();

                    d1 = Operation.find(symbol).perform(d1, d2);
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
