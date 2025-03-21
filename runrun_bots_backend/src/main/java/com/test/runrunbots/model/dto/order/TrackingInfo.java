package com.test.runrunbots.model.dto.order;

import lombok.Data;

@Data  
public class TrackingInfo {  
    private Long orderId;  
    private String trackingNumber;
    private String currentStatus;
    private String deliveryMethod;  
    private String estimatedArrivalTime;  
}  