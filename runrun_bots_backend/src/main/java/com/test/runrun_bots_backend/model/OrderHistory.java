package com.test.runrun_bots_backend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Order History
 */
@Entity
@Table(name = "order_history")  
public class OrderHistory {  
    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;  

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;  

    @Column(nullable = false)  
    @Enumerated(EnumType.STRING)  
    private OrderStatus status;  

    @Column(nullable = false)  
    @CreationTimestamp
    private LocalDateTime timestamp;

    // Getters and Setters


    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}