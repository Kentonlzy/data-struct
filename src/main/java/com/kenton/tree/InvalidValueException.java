package com.kenton.tree;

public class InvalidValueException extends Exception {
    public InvalidValueException() {
        super();
    }
    public InvalidValueException(String message) {
        super(message);
    }
    public InvalidValueException(Throwable cause) {
        super(cause);
    }
}
