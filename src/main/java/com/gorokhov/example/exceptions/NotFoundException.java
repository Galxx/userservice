package com.gorokhov.example.exceptions;

public class NotFoundException extends RuntimeException {

    private final String message;

    public NotFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}
