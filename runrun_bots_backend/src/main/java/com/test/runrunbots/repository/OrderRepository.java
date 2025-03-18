package com.test.runrunbots.repository;

import com.test.runrunbots.model.Order;
import com.test.runrunbots.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    List<Order> findByStatus(OrderStatus status);
}  