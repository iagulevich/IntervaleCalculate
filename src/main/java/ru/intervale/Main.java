package ru.intervale;

import ru.intervale.calculator.MultiCalculator;
import ru.intervale.calculator.dto.Result;
import ru.intervale.calculator.impl.ScientificCalculator;
import ru.intervale.calculator.impl.SimpleCalculator;
import ru.intervale.dao.Reader;
import ru.intervale.dao.Writer;
import ru.intervale.dao.file.FileDAO;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.*;

public class Main {

    public static void main(String[] args) {

        task1();

        task2();

    }

    private static void task1() {
        Reader input = new FileDAO("input_1.txt");
        final List<String> inputList = input.toList();

        MultiCalculator calculator = new SimpleCalculator();

        final List<Result> results = calculator.calculate(inputList);

        final List<String> outputList = results.stream().map(Result::getResult).collect(Collectors.toList());

        outputList.forEach(out::println);

        Writer output = new FileDAO("output_1.txt");
        output.write(outputList);
    }

    private static void task2() {
        Reader input = new FileDAO("input_2.txt");
        final List<String> inputList = input.toList();

        MultiCalculator calculator = new ScientificCalculator();

        final List<Result> results = calculator.calculate(inputList);

        final List<String> outputList = results.stream().map(Result::getResult).collect(Collectors.toList());

        outputList.forEach(out::println);

        Writer output = new FileDAO("output_2.txt");
        output.write(outputList);
    }

}
