package com.test.runrunbots.repository;

import com.test.runrunbots.model.Payment;
import com.test.runrunbots.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /**
     *
     * ‘ @Table(name = "payments") ’
     *
     * ‘ @ManyToOne
     *   @JoinColumn(name = "order_id", nullable = false) ’
     *     private Order order;
     *
     * @param orderId
     * @return
     */
    List<Payment> findByOrder_OrderId(Long orderId);

    Optional<Payment> findByOrder_OrderIdAndStatus(Long orderId, PaymentStatus status);
}  