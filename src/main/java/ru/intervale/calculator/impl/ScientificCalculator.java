package ru.intervale.calculator.impl;

import ru.intervale.calculator.dto.Result;

import java.util.Stack;
import java.util.StringTokenizer;

public class ScientificCalculator extends BaseCalculator {


    @Override
    public Result calculate(String expression) {

        if (expression.isEmpty()) {
            return new Result(expression, "Строка пуста!");
        }

        String sIn = addBrackets(expression);
        sIn = toRevPolNotation(sIn);

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
                return new Result(expression, "Недопустимый символ в выражении!");
            }
        }

        if (stack.size() > 1) {
            return new Result(expression, "Количество операторов не соответствует количеству операндов!");
        }
        return new Result(expression, stack.pop());
    }


    /* метод добавления скобок для определения приоритета возведения в степень */
    private String addBrackets(String stringIn) {
        String opStack = "";
        String stringOut = "";
        char charIn, charTmp;

        for (int i = 0; i < stringIn.length(); i++) {
            charIn = stringIn.charAt(i);
            if (isOperation(charIn)) {
                if (charIn == '^') {
                    opStack += charIn;
                    stringOut += charIn + "(";
                } else {
                    if (opStack.length() > 0) {
                        charTmp = opStack.charAt(opStack.length() - 1);
                        if ((charIn != charTmp && charTmp == '^')) {
                            while (charTmp == '^') {
                                stringOut += ")";
                                opStack = opStack.substring(0, opStack.length() - 1);
                                if (opStack.length() > 0) {
                                    charTmp = opStack.charAt(opStack.length() - 1);
                                } else {
                                    break;
                                }
                            }
                            stringOut += charIn;
                            opStack += charIn;
                            opStack = opStack.substring(0, opStack.length() - 1);

                        } else {
                            stringOut += charIn;
                        }
                    } else {
                        opStack += charIn;
                        stringOut += charIn;
                    }
                }
            } else {
                stringOut += charIn;
                if (opStack.length() > 0) {
                    charTmp = opStack.charAt(opStack.length() - 1);
                    if ((i == stringIn.length() - 1 && charTmp == '^')) {
                        while (charTmp == '^') {
                            stringOut += ")";
                            opStack = opStack.substring(0, opStack.length() - 1);
                            if (opStack.length() > 0) {
                                charTmp = opStack.charAt(opStack.length() - 1);
                            } else break;
                        }
                    }
                }
            }
        }
        return stringOut;
    }

    /* метод преобразования входной строки в ОПН с проверкой на унарный плюс и минус*/
    private String toRevPolNotation(String stringIn){

        boolean isSignOperation = false;
        boolean isLineBeginning = true;
        String strStack = "";
        String strOut = "";
        char charIn, charTemp;

        for (int i = 0; i < stringIn.length(); i++) {
            charIn = stringIn.charAt(i);
            if (isOperation(charIn)) {

                if (((isSignOperation && charIn == '-' && strStack.length() > 0) || (isLineBeginning)) ||
                        (isSignOperation && charIn == '+' && strStack.length() > 0) || (isLineBeginning)) {
                    strOut += " " + charIn;
                    continue;
                }
                isSignOperation = true;

                if (strStack.length() > 0) {
                    while (strStack.length() > 0) {
                        charTemp = strStack.charAt(strStack.length() - 1);
                        if (isOperation(charTemp) && (operationPrior(charIn) <= operationPrior(charTemp))) {
                            strOut += " " + charTemp + " ";
                            strStack = strStack.substring(0, strStack.length() - 1);
                        } else {
                            strOut += " ";
                            break;
                        }
                    }
                }
                strOut += " ";
                strStack += charIn;

            } else if ('(' == charIn) {

                strStack += charIn;

            } else if (')' == charIn) {

                if (strStack.length() > 0) {
                    if (strStack.charAt(strStack.length() - 1) == '(') {
                        strStack = strStack.substring(0, strStack.length() - 1);
                        if (strStack.length() > 0 || strStack.charAt(strStack.length() - 1) == '(') {
                            continue;
                        }

                    } else {
                        while ((strStack.charAt(strStack.length() - 1) != '(' && strStack.length() > 0)) {
                            strOut += " " + strStack.charAt(strStack.length() - 1);
                            strStack = strStack.substring(0, strStack.length() - 1);
                        }
                        strStack = strStack.substring(0, strStack.length() - 1);
                    }
                }
            } else {
                if (Character.isDigit(charIn)) {
                    isSignOperation = false;
                    isLineBeginning = false;
                }
                strOut += charIn;
            }
        }

        while (strStack.length() > 0) {
            if (strStack.charAt(strStack.length() - 1) == '(' ||
                    strStack.charAt(strStack.length() - 1) == ')') {
//                throw new Exception("Неверное количество открытых и закрытых скобок!");
            }
            strOut += " " + strStack.charAt(strStack.length() - 1);
            strStack = strStack.substring(0, strStack.length() - 1);
        }
        return strOut;
    }

}
