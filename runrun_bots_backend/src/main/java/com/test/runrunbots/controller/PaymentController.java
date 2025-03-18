package com.test.runrunbots.controller;

import com.test.runrunbots.model.dto.payment.CreatePaymentRequest;
import com.test.runrunbots.model.dto.payment.PaymentDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {  
    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody CreatePaymentRequest request) {
        // 创建支付逻辑  
        return null;
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentDTO> getPaymentStatus(@PathVariable Long paymentId) {
        // 获取支付状态逻辑  
        return null;
    }
}  