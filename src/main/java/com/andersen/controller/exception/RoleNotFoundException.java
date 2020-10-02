package com.andersen.controller.exception;

public class RoleNotFoundException extends RuntimeException {

    private static final String ROLE_NOT_FOUND_BY_ID = "Role with id=%s not found";
    private static final String ROLE_NOT_FOUND_BY_NAME = "Role with name=%s not found";

    private String message;

    public RoleNotFoundException(long id) {
        super(String.format(ROLE_NOT_FOUND_BY_ID, id));
        message = String.format(ROLE_NOT_FOUND_BY_ID, id);
    }

    public RoleNotFoundException(String name) {
        super(String.format(ROLE_NOT_FOUND_BY_NAME, name));
        message = String.format(ROLE_NOT_FOUND_BY_NAME, name);
    }

    @Override
    public String getMessage() {
        return message;
    }

}