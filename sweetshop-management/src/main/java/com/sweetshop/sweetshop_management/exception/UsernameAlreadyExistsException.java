package com.sweetshop.sweetshop_management.exception;

public class UsernameAlreadyExistsException extends RuntimeException {
  public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
