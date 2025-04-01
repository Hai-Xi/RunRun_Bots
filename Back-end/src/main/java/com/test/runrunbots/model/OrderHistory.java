package com.test.runrunbots.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Order History
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order_history")  
public class OrderHistory {  
    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;  

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)  // Foreign key to the Order table
    private Order order;  

    @Column(nullable = false)  
    @Enumerated(EnumType.STRING)  
    private OrderStatus status;  

    @Column(nullable = false)  
    @CreationTimestamp
    private LocalDateTime timestamp;

    // Getters and Setters


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderHistory that = (OrderHistory) o;
        return Objects.equals(historyId, that.historyId) && Objects.equals(order, that.order) && status == that.status && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(historyId, order, status, timestamp);
    }

    @Override
    public String toString() {
        return "OrderHistory{" +
                "historyId=" + historyId +
                ", order=" + order +
                ", status=" + status +
                ", timestamp=" + timestamp +
                '}';
    }
}