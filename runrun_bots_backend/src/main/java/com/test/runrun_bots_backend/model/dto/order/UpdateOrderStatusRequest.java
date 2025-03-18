package com.test.runrun_bots_backend.model.dto.order;

import lombok.Data;

@Data  
public class UpdateOrderStatusRequest {  
    private String status; // PENDING, PAID, IN_PROGRESS, DELIVERED, CANCELLED  
}  