package com.gorokhov.example.exceptions;

public class DeleteRoleException extends RuntimeException {


    private final String message;

    public DeleteRoleException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}
