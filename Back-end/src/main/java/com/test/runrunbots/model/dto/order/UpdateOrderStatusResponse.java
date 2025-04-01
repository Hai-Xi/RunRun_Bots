package com.test.runrunbots.model.dto.order;

import lombok.Data;

@Data  
public class UpdateOrderStatusResponse {
    private String status; // PENDING, PAID, IN_PROGRESS, DELIVERED, CANCELLED  
}  