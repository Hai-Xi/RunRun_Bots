package com.test.runrunbots.model.dto.order;


public class OrderIdNotExistException extends RuntimeException {


   public OrderIdNotExistException() {
       super("OrderId not exists");
   }
}
