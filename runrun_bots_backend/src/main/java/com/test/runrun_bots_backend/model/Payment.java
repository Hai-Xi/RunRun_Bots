package com.test.runrun_bots_backend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Payments
 */
@Entity
@Table(name = "payments")  
public class Payment {  
    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;  

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;  

    @Column(nullable = false)  
    private BigDecimal amount;

    @Column(name = "payment_method", nullable = false)  
    @Enumerated(EnumType.STRING)  
    private PaymentMethod paymentMethod;  

    @Column(nullable = false)  
    @Enumerated(EnumType.STRING)  
    private PaymentStatus status;  

    @Column(name = "created_at")  
    @CreationTimestamp
    private LocalDateTime createdAt;

    // Getters and Setters


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}

