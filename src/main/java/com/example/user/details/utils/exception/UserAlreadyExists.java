package com.example.user.details.utils.exception;

public class UserAlreadyExists extends RuntimeException {

    public UserAlreadyExists(String message) {
        super(message);
    }
}
