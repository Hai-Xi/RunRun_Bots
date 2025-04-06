package com.test.runrunbots.controller;


public class UserAlreadyExistException extends RuntimeException {


   public UserAlreadyExistException() {
       super("Username already exists");
   }
}
