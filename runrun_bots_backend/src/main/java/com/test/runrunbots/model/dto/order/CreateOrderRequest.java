package com.test.runrunbots.model.dto.order;

import lombok.Data;

@Data  
public class CreateOrderRequest {
    /**
     * prder table
     */
    private Long userId;
    private String itemDescription;  
    private String pickupLocation;  
    private String deliveryLocation;  
    private String deliveryMethod; // ROBOT or DRONE

    /**
     * payment table
     */
    private Double totalAmount;   // Total order amount
    private String paymentMethod; // Payment method, e.g., Credit Card
    private String paymentStatus; // Payment status, e.g., PENDING

}  