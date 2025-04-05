package com.test.runrunbots.model.dto.order;

import com.test.runrunbots.model.DeliveryMethod;
import com.test.runrunbots.model.dto.valid.ValidDeliveryMethod;
import lombok.Data;

import java.time.LocalDateTime;

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
     *
     * LocalDateTime 是 java.time 引入的类型，在序列化为 JSON 时需要确保其格式可读。通常用到 Jackson 或别的序列化工具
     *
     */
    private LocalDateTime estimatedArrivalTime;

    /**
     * payment table
     */
    private Double totalAmount;   // Total order amount
    private String paymentMethod; // Payment method, e.g., Credit Card
    private String paymentStatus; // Payment status, e.g., PENDING

}  