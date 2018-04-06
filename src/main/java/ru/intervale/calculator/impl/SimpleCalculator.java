package ru.intervale.calculator.impl;

import ru.intervale.calculator.algorithm.ReversePolishNotation;
import ru.intervale.calculator.dto.Result;
import ru.intervale.calculator.CalculatorException;
import ru.intervale.calculator.operation.Operation;

import java.util.Stack;
import java.util.StringTokenizer;

public class SimpleCalculator extends BaseCalculator {

    @Override
    public Result calculate(String expression) {

        Stack<Double> stack = new Stack<>();

        try {
            String rpn = new ReversePolishNotation(expression).rpn();
            System.out.println("rpn: " + rpn);

            double d1;
            double pop1;
            double pop2;

            StringTokenizer tokenizer = new StringTokenizer(rpn);

            String token;

            while (tokenizer.hasMoreTokens()) {
                token = tokenizer.nextToken().trim();
                pop1 = stack.pop();
                pop2 = stack.pop();
                d1 = isOperation(token) ? Operation.find(token.charAt(0)).perform(pop2, pop1) : Double.parseDouble(token);
                stack.push(d1);
            }
            if (stack.size() > 1) {
                throw new CalculatorException("Количество операторов не соответствует количеству операндов.");
            }
        } catch (CalculatorException e) {
            return new Result(expression, e.getMessage());
        }
        return new Result(expression, stack.pop());
    }

}
