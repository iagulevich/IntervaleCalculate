package ru.intervale.calculator.dto;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Result {

    private final String input;

    private final Number result;

    private final String errorMessage;

    public Result(String input, Number result) {
        this.input = input;
        this.result = result;
        this.errorMessage = null;
    }

    public Result(String input, String errorMessage) {
        this.input = input;
        this.errorMessage = errorMessage;
        this.result = null;
    }

    public boolean hasError() {
        return this.errorMessage != null;
    }

    public String getResult() {
        return input + (hasError() ? " - " + getErrorMessage() : "=" + format());
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String format() {
        Locale locale = new Locale("en", "UK");
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(locale);
        String formatDouble;
        if (result.doubleValue() % 1 == 0) {
            formatDouble = "##0";
        } else {
            formatDouble = "##0.00000";
        }
        DecimalFormat decimalFormat = new DecimalFormat(formatDouble, decimalFormatSymbols);
        return decimalFormat.format(result);
    }

}
