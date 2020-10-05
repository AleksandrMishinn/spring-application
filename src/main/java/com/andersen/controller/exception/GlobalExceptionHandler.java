package com.andersen.controller.exception;

import com.andersen.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Response<?>> handleException(UserNotFoundException ex) {
        Response<?> error = new Response<>();
        error.setMessage(ex.getMessage());
        error.setStatus(404);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Response<?>> handleException(RoleNotFoundException ex) {
        Response<?> error = new Response<>();
        error.setMessage(ex.getMessage());
        error.setStatus(404);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Response<?>> handleException(Exception ex) {
        Response<?> error = new Response<>();
        error.setMessage(ex.getMessage());
        error.setStatus(400);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
