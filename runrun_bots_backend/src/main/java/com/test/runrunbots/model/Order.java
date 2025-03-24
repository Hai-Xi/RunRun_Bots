package com.test.runrunbots.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Orders
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    // The @Enumerated(EnumType.STRING) annotation ensures that
    // the enum values are stored as
    // strings in the database (e.g., "ADMIN", "USER", "GUEST").
    @Enumerated(EnumType.STRING)
    private DeliveryMethod deliveryMethod;

    @Column(nullable = false)  
    @Enumerated(EnumType.STRING)  
    private OrderStatus status;  

    @Column(name = "created_at")  
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     *
     * mappedBy: 被维护端使用，表示由另一端 (即 order )的哪个字段来维护这段关系。这里 Payment 的 order 是拥有端。
     * cascade = CascadeType.ALL: 表示在操作 Order 实体时，相关联的 Payment 实体也会被自动操作。例如创建 Order 时，会自动保存 Payment。
     *
     */
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;

    @OneToMany(mappedBy = "order")  
    private List<OrderHistory> orderHistory;

    // Getters and Setters


}

