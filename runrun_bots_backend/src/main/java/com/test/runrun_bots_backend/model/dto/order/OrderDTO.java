package com.test.runrun_bots_backend.model.dto.order;

import lombok.Data;

import java.time.LocalDateTime;  

@Data  
public class OrderDTO {  
    private Long orderId;  
    private Long userId;  
    private String itemDescription;  
    private String pickupLocation;  
    private String deliveryLocation;  
    private String deliveryMethod;  
    private String status;  
    private LocalDateTime createdAt;  
}  