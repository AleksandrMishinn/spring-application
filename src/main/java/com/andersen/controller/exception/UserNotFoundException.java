package com.andersen.controller.exception;

public class UserNotFoundException extends RuntimeException {

    private static final String USER_NOT_FOUND = "User with id=%s not found";

    private String message;

    public UserNotFoundException(long id) {
        super(String.format(USER_NOT_FOUND, id));
        message = String.format(USER_NOT_FOUND, id);
    }

    @Override
    public String getMessage() {
        return message;
    }

}
