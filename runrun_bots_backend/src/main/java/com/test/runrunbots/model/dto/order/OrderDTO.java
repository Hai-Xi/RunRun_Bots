package com.test.runrunbots.model.dto.order;

import com.test.runrunbots.model.DeliveryMethod;
import com.test.runrunbots.model.Order;
import com.test.runrunbots.model.OrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;  


@Data
public class OrderDTO {
    private Long orderId;  
    private Long userId;  
    private String itemDescription;  
    private String pickupLocation;  
    private String deliveryLocation;  
    private DeliveryMethod deliveryMethod;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}