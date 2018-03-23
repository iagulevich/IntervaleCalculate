package ru.intervale.calculator;

import ru.intervale.calculator.dto.Result;
import ru.intervale.calculator.task1.IntervaleCalculator1;
import ru.intervale.dao.Reader;
import ru.intervale.dao.Writer;
import ru.intervale.dao.file.FileDAO;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        Reader input = new FileDAO("input_1.txt");
        final List<String> inputList = input.toList();

        IntervaleCalculator1 calculator = new IntervaleCalculator1();

        final List<String> outputList = input.toList();
        Result result;
        for (String strIn : inputList) {
            result = calculator.calculate(strIn);
            System.out.println(result.getResult());
            outputList.add(result.getResult());
        }
        Writer output = new FileDAO("output_1.txt");
        output.write(outputList);

    }
}
