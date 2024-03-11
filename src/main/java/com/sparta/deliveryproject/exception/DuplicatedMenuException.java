package com.sparta.deliveryproject.exception;

public class DuplicatedMenuException extends IllegalArgumentException{
    public DuplicatedMenuException() {
    }

    public DuplicatedMenuException(String message){
        super(message);
    }

    public DuplicatedMenuException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedMenuException(Throwable cause) {
        super(cause);
    }
}
