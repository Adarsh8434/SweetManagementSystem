package com.sweetshop.sweetshop_management.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<String> handleDuplicateUsername(UsernameAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidCredientialsException.class)
    public ResponseEntity<String> handleInvalidCredentials(InvalidCredientialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
      @ExceptionHandler(SweetNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleSweetNotFound(SweetNotFoundException ex) {
        return ex.getMessage();
    }
}
