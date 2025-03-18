package com.test.runrun_bots_backend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Orders
 */
@Entity
@Table(name = "orders")  
public class Order {  
    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;  

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)  
    private User user;  

    @Column(name = "item_description", nullable = false)  
    private String itemDescription;  

    @Column(name = "pickup_location", nullable = false)  
    private String pickupLocation;  

    @Column(name = "delivery_location", nullable = false)
    private String deliveryLocation;  

    @Column(name = "delivery_method", nullable = false)  
    @Enumerated(EnumType.STRING)  
    private DeliveryMethod deliveryMethod;  

    @Column(nullable = false)  
    @Enumerated(EnumType.STRING)  
    private OrderStatus status;  

    @Column(name = "created_at")  
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order")  
    private List<Payment> payments;

    @OneToMany(mappedBy = "order")  
    private List<OrderHistory> orderHistory;  

    // Getters and Setters

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public List<OrderHistory> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<OrderHistory> orderHistory) {
        this.orderHistory = orderHistory;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}

