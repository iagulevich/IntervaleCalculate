package ru.intervale.calculator.task2;

import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Stack;
import java.util.StringTokenizer;
import static jdk.nashorn.internal.objects.Global.Infinity;

public class IntervaleCalculator2 {

    /* метод считывания и записи в файл с одновременным вычислением ПОСТРОЧНО !*/
    public  void readWrite() throws IOException {

        FileReader fr;

        try {
            fr = new FileReader("src\\main\\resources\\input_2.txt");
        } catch (FileNotFoundException e) {
            throw  new FileNotFoundException("Файл не найден!");
        }

        BufferedReader br = new BufferedReader(fr);
        String strIn, strOut;
        double resCalculate = 0;

        FileWriter fw ;

        try {
            fw = new FileWriter("src\\main\\resources\\output_2.txt");
        } catch (IOException e) {
            throw  new IOException("Ошибка записи в файл!");
        }

        String formatDouble;

        while ( (strIn = br.readLine()) != null ){

            try {
                strOut = strIn;
                strIn = addBrackets(strIn);
                strIn = toRevPolNotation(strIn);
                resCalculate = calculate(strIn);
                Locale locale = new Locale("en", "UK");
                DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(locale);

                if (resCalculate%1 == 0) {
                    formatDouble = "##0";
                } else {
                    formatDouble = "##0.00000";
                }
                DecimalFormat decimalFormat = new DecimalFormat(formatDouble, decimalFormatSymbols);
                String format = decimalFormat.format(resCalculate);

                if (resCalculate == Infinity) {
                    strOut = "Деление на 0!";
                    fw.write(strOut +"\n");
                } else {
                    fw.write(strOut + "=" + format + "\n");
                }
                fw.flush();
            } catch (Exception e) {
                fw.write(e.getMessage() + "\n");
            }
        }
        fw.close();
        br.close();
        fr.close();
    }

    /* метод добавления скобок для определения приоритета возведения в степень */
    private static String addBrackets(String stringIn){
        String opStack = "";
        String stringOut = "";
        char charIn, charTmp;

        for (int i = 0; i < stringIn.length(); i++) {
            charIn = stringIn.charAt(i);
            if (isOperation(charIn)) {
                if ( charIn == '^') {
                    opStack += charIn;
                    stringOut += charIn + "(";
                } else {
                    if (opStack.length() > 0) {
                        charTmp = opStack.charAt(opStack.length() - 1);
                        if ((charIn != charTmp && charTmp == '^')) {
                            while (charTmp == '^') {
                                stringOut += ")";
                                opStack = opStack.substring(0,opStack.length()-1);
                                if (opStack.length() > 0){
                                    charTmp = opStack.charAt(opStack.length()-1);
                                } else {
                                    break;
                                }
                            }
                            stringOut += charIn;
                            opStack += charIn;
                            opStack = opStack.substring(0,opStack.length()-1);

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
                if (opStack.length()>0){
                    charTmp = opStack.charAt(opStack.length()-1);
                    if ((i == stringIn.length() - 1 &&  charTmp == '^')) {
                        while (charTmp == '^' ) {
                            stringOut += ")";
                            opStack = opStack.substring(0,opStack.length()-1);
                            if (opStack.length() > 0) {
                                charTmp = opStack.charAt(opStack.length() - 1);
                            }else break;
                        }
                    }
                }
            }
        }
        return  stringOut;
    }

    /* метод преобразования входной строки в ОПН с проверкой на унарный плюс и минус*/
    private static String toRevPolNotation(String stringIn) throws Exception {

        boolean isSignOperation = false;
        boolean isLineBeginning = true;
        String strStack = "";
        String strOut = "";
        char charIn, charTemp;

        for (int i = 0; i < stringIn.length(); i++) {
            charIn = stringIn.charAt(i);
            if (isOperation(charIn)) {

                if (((isSignOperation && charIn == '-' && strStack.length() > 0 ) || (isLineBeginning)) ||
                        (isSignOperation && charIn == '+' && strStack.length() > 0 ) || (isLineBeginning)) {
                    strOut += " " + charIn;
                    continue;
                }
                isSignOperation = true;

                if (strStack.length() > 0) {
                    while (strStack.length() > 0) {
                        charTemp = strStack.charAt(strStack.length()-1);
                        if (isOperation(charTemp) && (operationPrior(charIn) <= operationPrior(charTemp))) {
                            strOut += " " + charTemp + " ";
                            strStack = strStack.substring(0, strStack.length()-1);
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

                if (strStack.length()>0) {
                    if (strStack.charAt(strStack.length()-1) == '(') {
                        strStack = strStack.substring(0, strStack.length()-1);
                        if (strStack.length()>0 || strStack.charAt(strStack.length()-1) == '('){
                            continue;
                        }

                    } else {
                        while ((strStack.charAt(strStack.length()-1) != '(' && strStack.length() > 0) ) {
                            strOut += " " + strStack.charAt(strStack.length()-1);
                            strStack = strStack.substring(0, strStack.length()-1);
                        }
                        strStack = strStack.substring(0, strStack.length()-1);
                    }
                }
            } else{
                    if(Character.isDigit(charIn)){
                    isSignOperation = false;
                    isLineBeginning = false;
                    }
                    strOut += charIn;
                }
            }

        while (strStack.length() > 0) {
            if (strStack.charAt(strStack.length()-1) == '(' ||
                    strStack.charAt(strStack.length()-1) == ')') {
                throw new Exception("Неверное количество открытых и закрытых скобок!");
            }
            strOut += " " + strStack.charAt(strStack.length()-1);
            strStack = strStack.substring(0, strStack.length()-1);
        }
        return  strOut;
    }

    /* является ли символ оператором */
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

    /* приоритет операций */
    private static byte operationPrior(char op) {
        switch (op) {
            case '^':
                return 3;
            case '*':
            case '/':
            case '%':
                return 2;
        }
        return 1;
    }

    /* метод для вычислений  */
    public static double calculate(String sIn) throws Exception {

        if (sIn == ""){
            throw new Exception("Строка пуста!");
        }

        int forPercent = 100;
        double d1 = 0, d2 = 0;
        String strTmp;
        Stack<Double> stack = new Stack <>();
        StringTokenizer strToken = new StringTokenizer(sIn);

        while(strToken.hasMoreTokens()) {
            try {
                strTmp = strToken.nextToken().trim();
                if (1 == strTmp.length() && isOperation(strTmp.charAt(0))) {
                    if (stack.size() < 2) {
                        throw new Exception("Неверное количество данных в стеке для операции " + strTmp);
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
                            d1 = Math.pow(d2,d1);
                            break;
                    }
                    stack.push(d1);
                } else {
                    d1 = Double.parseDouble(strTmp);
                    stack.push(d1);
                }

            } catch (Exception e) {
                throw new Exception("Недопустимый символ в выражении!");
            }
        }

        if (stack.size() > 1) {
            throw new Exception("Количество операторов не соответствует количеству операндов!");
        }
        return stack.pop();
    }
}