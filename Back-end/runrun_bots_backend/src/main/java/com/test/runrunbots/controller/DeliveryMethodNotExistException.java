package com.test.runrunbots.controller;


import org.springframework.http.converter.HttpMessageNotReadableException;

public class DeliveryMethodNotExistException extends HttpMessageNotReadableException {


   public DeliveryMethodNotExistException() {
       super("deliveryMethod not exists");
   }
}
