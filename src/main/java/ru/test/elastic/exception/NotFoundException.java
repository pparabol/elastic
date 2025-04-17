package ru.test.elastic.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("The required entity was not found");
    }
}
