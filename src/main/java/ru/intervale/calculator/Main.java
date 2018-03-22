package ru.intervale.calculator;

import ru.intervale.file.FileResource;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        FileResource input1 = new FileResource("input_1.txt");
        final List<String> list = input1.toList();
        System.out.println(list);
        FileResource output1 = new FileResource("output_1.txt");
        output1.write(list);

    }
}
