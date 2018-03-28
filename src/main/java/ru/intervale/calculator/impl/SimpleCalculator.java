package ru.intervale.calculator.impl;

import ru.intervale.calculator.algorithm.ReversePolishNotation;
import ru.intervale.calculator.dto.Result;
import ru.intervale.calculator.exceptions.CalculatorException;
import ru.intervale.calculator.operation.Operation;

import java.util.Stack;
import java.util.StringTokenizer;

public class SimpleCalculator extends BaseCalculator {

    @Override
    public Result calculate(String expression) {

        String rpn = new ReversePolishNotation(expression).rpn();
        System.out.println("rpn: " + rpn);

        double d1;
        String token;

        Stack<Double> stack = new Stack<>();
        StringTokenizer tokenizer = new StringTokenizer(rpn);

        char symbol;
        try {
            while (tokenizer.hasMoreTokens()) {
                token = tokenizer.nextToken().trim();
                symbol = token.charAt(0);
                if (1 == token.length() && isOperation(symbol)) {
                    if (stack.size() < 2) {
                        throw new CalculatorException("Неверное количество данных в стеке для операции " + token);
                    }
                    d1 = Operation.find(symbol).perform(stack.pop(), stack.pop());
                } else {
                    d1 = Double.parseDouble(token);
                }
                stack.push(d1);
            }
        } catch (CalculatorException e) {
            return new Result(expression, e.getMessage());
        } catch (Exception e) {
            return new Result(expression, "Недопустимый символ в выражении!");
        }

        return stack.size() > 1 ? new Result(expression, "Количество операторов не соответствует количеству операндов!") : new Result(expression, stack.pop());
    }

}
