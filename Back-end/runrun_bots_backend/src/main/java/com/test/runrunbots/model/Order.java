package com.test.runrunbots.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
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

    /**
     *
     * 如果你的 Order 实体中有 User 的双向关系，可能会在序列化为 JSON 时引发循环引用问题。
     * 例如，Order 实体中有 User user，而 User 实体中又包含 List<Order> 这样的映射关系。
     * 当使用 Jackson 序列化输出时，Spring 会递归访问字段，导致 StackOverflowError 或类似的错误。
     *
     * 解决循环引用
     * 采用 @JsonIgnore 或 @JsonManagedReference / @JsonBackReference：
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore // 忽略序列化 User 字段
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

    /**
     * 
     * 如果你的项目仍在使用 java.util.Date，确保使用 @Temporal 注解指定正确的映射类型。
     *
     * 从 Java 8 开始，可以使用更现代的 java.time 包中的类（如 LocalDate、LocalTime、LocalDateTime 等）。使用这些类型时，不需要 @Temporal 注解，因为它们已经具有明确的时间语义
     *
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at",nullable = false, updatable = false)  // 创建时间不可更新
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     *
     * 添加预估到达时间字段
     *
     * 使用 LocalDateTime 类型存储日期和时间信息，这是 Java 8 引入的新日期时间 API，比 Date 类更加灵活和易用。
     * 使用 @Column 注解显式指定字段名称，确保与数据库字段名匹配。
     *
     */
    @Column(name = "estimated_arrival_time")
    private LocalDateTime estimatedArrivalTime;

    /**
     *
     * mappedBy: 被维护端使用，表示由另一端 (即 Payment表 )(即 带有 @JoinColumn注解的 table, 用于定义外键列)的哪个字段来维护这段关系。这里 Payment 的 order字段 是拥有端。
     * cascade = CascadeType.ALL: 表示在操作 Order 实体时，相关联的 Payment 实体也会被自动操作。例如创建 Order 时，会自动保存 Payment。
     *
     */
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    //    @JsonIgnore // 忽略序列化 Payment 字段
    private Payment payment;

    @OneToMany(mappedBy = "order")
    @JsonIgnore // 忽略序列化 List<OrderHistory> 字段
    private List<OrderHistory> orderHistory;

    // Getters and Setters


}

