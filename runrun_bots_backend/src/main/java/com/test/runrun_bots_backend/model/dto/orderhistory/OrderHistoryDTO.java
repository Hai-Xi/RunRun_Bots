package com.test.runrun_bots_backend.model.dto.orderhistory;

import lombok.Data;

import java.time.LocalDateTime;  

@Data  
public class OrderHistoryDTO {  
    private Long historyId;  
    private Long orderId;  
    private String status; // PENDING, PAID, IN_PROGRESS, DELIVERED, CANCELLED  
    private LocalDateTime timestamp;  
}  