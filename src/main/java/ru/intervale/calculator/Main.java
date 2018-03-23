package ru.intervale.calculator;

import ru.intervale.calculator.impl.SimpleCalculator;
import ru.intervale.dao.Reader;
import ru.intervale.dao.Writer;
import ru.intervale.dao.file.FileDAO;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Reader input = new FileDAO("input_1.txt");
        final List<String> inputList = input.toList();

        Calculator calculator = new SimpleCalculator();

        final List<String> outputList = new ArrayList<>(inputList.size());

        inputList.forEach(s -> outputList.add(calculator.calculate(s).getResult()));

        System.out.println(outputList);

        Writer output = new FileDAO("output_1.txt");
        output.write(outputList);

    }
}
