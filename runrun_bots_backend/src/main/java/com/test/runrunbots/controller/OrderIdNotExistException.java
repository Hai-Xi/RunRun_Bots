package com.test.runrunbots.controller;


public class OrderIdNotExistException extends RuntimeException {


   public OrderIdNotExistException() {
       super("OrderId not exists");
   }
}
