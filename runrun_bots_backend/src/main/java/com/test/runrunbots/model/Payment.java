package com.test.runrunbots.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Payments
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    private PaymentMethod paymentMethod;  // PaymentMethod：Credit Card, PayPal

    @Column(nullable = false)  
    @Enumerated(EnumType.STRING)  
    private PaymentStatus status;  // PaymentStatus：PENDING, COMPLETED

    @Column(name = "created_at")  
    @CreationTimestamp
    private LocalDateTime createdAt;

    // Getters and Setters


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(paymentId, payment.paymentId) && Objects.equals(order, payment.order) && Objects.equals(amount, payment.amount) && paymentMethod == payment.paymentMethod && status == payment.status && Objects.equals(createdAt, payment.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId, order, amount, paymentMethod, status, createdAt);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "amount=" + amount +
                ", paymentId=" + paymentId +
                ", order=" + order +
                ", paymentMethod=" + paymentMethod +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }

}

