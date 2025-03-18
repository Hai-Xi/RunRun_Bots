package com.test.runrun_bots_backend.model.dto.payment;

import lombok.Data;

import java.math.BigDecimal;  

@Data  
public class CreatePaymentRequest {  
    private Long orderId;  
    private BigDecimal amount;  
    private String paymentMethod; // CREDIT_CARD, PAYPAL, APPLE_PAY  
}  