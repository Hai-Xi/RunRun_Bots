package com.test.runrunbots.model.dto.order;

import com.test.runrunbots.model.OrderStatus;
import lombok.Data;

@Data  
public class UpdateOrderStatusRequest {  
    private OrderStatus status; // PENDING, PAID, IN_PROGRESS, DELIVERED, CANCELLED
}  