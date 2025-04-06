package com.test.runrunbots.repository;

import com.test.runrunbots.model.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

    /**
     * Find all OrderHistory records for a specific Order, sorted by timestamp (descending)
     *
     * Spring Data JPA uses the field names in the entity to construct queries,
     * so the method name should reference the actual field names in the OrderHistory entity.
     *
     * If you want to find all OrderHistory records for a specific Order and sort them by timestamp in descending order, the correct method name should be:
     *
     * @param orderId
     * @return
     */
    List<OrderHistory> findByOrder_OrderIdOrderByTimestampDesc(Long orderId);
}  