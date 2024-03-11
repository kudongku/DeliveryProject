package com.sparta.deliveryproject.exception;

public class NotValidCategoryException extends IllegalArgumentException {
    public NotValidCategoryException(String message) {
        super(message);
    }

    public NotValidCategoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotValidCategoryException(Throwable cause) {
        super(cause);
    }

    public NotValidCategoryException() {
    }
}
