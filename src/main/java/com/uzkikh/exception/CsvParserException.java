package com.uzkikh.exception;

public class CsvParserException extends RuntimeException {

    public CsvParserException(String message) {
        super(message);
    }

    public CsvParserException(String message, Throwable e) {
        super(message, e);
    }
}
