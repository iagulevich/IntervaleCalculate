package ru.intervale.dao;

public class IORuntimeException extends RuntimeException {

    public IORuntimeException(String message) {
        super(message);
    }

    public IORuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
