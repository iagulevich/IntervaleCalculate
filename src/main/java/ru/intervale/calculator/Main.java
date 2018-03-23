package ru.intervale.calculator;

import ru.intervale.calculator.dto.Result;
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

        MultiCalculator calculator = new SimpleCalculator();

        final List<Result> results = calculator.calculate(inputList);

        final List<String> outputList = new ArrayList<>(inputList.size());

        results.forEach(result -> outputList.add(result.getResult()));

        System.out.println(outputList);

        Writer output = new FileDAO("output_1.txt");
        output.write(outputList);

    }
}
