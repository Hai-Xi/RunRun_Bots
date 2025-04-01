package com.test.runrunbots.model.dto.payment;

import lombok.Data;

import java.math.BigDecimal;  
import java.time.LocalDateTime;  

@Data  
public class PaymentDTO {  
    private Long paymentId;  
    private Long orderId;  
    private BigDecimal amount;  
    private String paymentMethod; // CREDIT_CARD, PAYPAL, APPLE_PAY  
    private String status;        // PENDING, SUCCESS, FAILED  
    private LocalDateTime createdAt;  
}  