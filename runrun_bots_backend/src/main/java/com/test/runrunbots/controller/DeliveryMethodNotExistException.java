package com.test.runrunbots.controller;


public class DeliveryMethodNotExistException extends RuntimeException {


   public DeliveryMethodNotExistException() {
       super("deliveryMethod not exists");
   }
}
