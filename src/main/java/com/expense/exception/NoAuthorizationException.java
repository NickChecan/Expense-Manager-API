package com.expense.exception;

public class NoAuthorizationException extends RuntimeException {

    public NoAuthorizationException(String message) {
        super(message);
    }

    public NoAuthorizationException() {
        super("Invalid Operation.");
    }

}
