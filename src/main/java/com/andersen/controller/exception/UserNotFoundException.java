package com.andersen.controller.exception;

public class UserNotFoundException extends RuntimeException {

    private static final String USER_NOT_FOUND = "User with id=%s not found";

    public UserNotFoundException(long id) {
        super(String.format(USER_NOT_FOUND, id));
    }
}
