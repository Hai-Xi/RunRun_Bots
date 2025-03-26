package com.test.runrunbots.model.dto.order;

import com.test.runrunbots.model.DeliveryMethod;
import com.test.runrunbots.model.dto.valid.ValidDeliveryMethod;
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

    @ValidDeliveryMethod
    private DeliveryMethod deliveryMethod; // ROBOT or DRONE

    /**
     * payment table
     */
    private Double totalAmount;   // Total order amount
    private String paymentMethod; // Payment method, e.g., Credit Card
    private String paymentStatus; // Payment status, e.g., PENDING

}  