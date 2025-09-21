package com.sweetshop.sweetshop_management.exception;

public class UserNotFoundException extends RuntimeException{
   public UserNotFoundException(String message){
       super(message);
   }
}
