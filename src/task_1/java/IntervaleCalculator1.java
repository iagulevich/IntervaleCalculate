package task_1.java;

import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Stack;
import java.util.StringTokenizer;
import static jdk.nashorn.internal.objects.Global.Infinity;

public class IntervaleCalculator1 {

    public  void readWrite() throws IOException {

        FileReader fr;

        try {
            fr = new FileReader("src/task_1/resources/input_1.txt");
        } catch (FileNotFoundException e) {
            throw  new FileNotFoundException("Файл не найден!");
        }

        BufferedReader br = new BufferedReader(fr);
        String strIn, strOut;
        double resCalculate = 0;

        File file = new File("src/task_1/resources/output_1.txt");
        FileWriter fw ;

        try {
            fw = new FileWriter(file);
        } catch (IOException e) {
            throw  new IOException("Ошибка записи в файл!");
        }

        String formatDouble;

        while ( (strIn = br.readLine()) != null ){

            try {
                strOut = strIn;
                strIn = revPolNotation(strIn);
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


    private static String revPolNotation(String stringIn) throws Exception {

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
        if (strStack.length() >0 ) {
            strOut += " " + strStack.charAt(strStack.length()-1);
            strStack.substring(strStack.length()-1);
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

