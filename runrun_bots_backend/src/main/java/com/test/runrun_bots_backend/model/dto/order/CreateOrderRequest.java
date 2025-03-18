package com.test.runrun_bots_backend.model.dto.order;

import lombok.Data;

@Data  
public class CreateOrderRequest {  
    private Long userId;  
    private String itemDescription;  
    private String pickupLocation;  
    private String deliveryLocation;  
    private String deliveryMethod; // ROBOT or DRONE  
}  