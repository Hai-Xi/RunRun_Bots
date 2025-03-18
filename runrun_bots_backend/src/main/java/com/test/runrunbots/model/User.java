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
 * users
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")  
public class User {  
    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;  

    @Column(nullable = false, length = 100)
    private String name;  

    @Column(nullable = false, unique = true, length = 100)  
    private String email;  

    @Column(nullable = false, unique = true, length = 15)  
    private String phone;  

    @Column(nullable = false)  
    private String password;  

    @Column(name = "created_at")  
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    // Getters and Setters


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(phone, user.phone) && Objects.equals(password, user.password) && Objects.equals(createdAt, user.createdAt) && Objects.equals(orders, user.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, email, phone, password, createdAt, orders);
    }

    @Override
    public String toString() {
        return "User{" +
                "createdAt=" + createdAt +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", orders=" + orders +
                '}';
    }
}