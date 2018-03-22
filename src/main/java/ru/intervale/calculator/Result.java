package ru.intervale.calculator;

public class Result {

    private final Number result;

    private final String errorMessage;

    public Result(Number result) {
        this.result = result;
        this.errorMessage = null;
    }

    public Result(String errorMessage) {
        this.errorMessage = errorMessage;
        this.result = null;
    }

    public boolean hasError() {
        return this.errorMessage != null;
    }

    public boolean hasNotError() {
        return !hasError();
    }

    public String getResult() {
        return result.toString();
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
