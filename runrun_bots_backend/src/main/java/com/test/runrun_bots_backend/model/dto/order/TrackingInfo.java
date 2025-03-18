package com.test.runrun_bots_backend.model.dto.order;

import lombok.Data;

@Data  
public class TrackingInfo {  
    private Long orderId;  
    private String currentStatus;  
    private String deliveryMethod;  
    private String estimatedArrivalTime;  
}  