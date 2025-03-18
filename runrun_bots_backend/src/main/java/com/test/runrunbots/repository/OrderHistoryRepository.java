package com.test.runrunbots.repository;

import com.test.runrunbots.model.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
    List<OrderHistory> findByOrderIdOrderByTimestampDesc(Long orderId);
}  