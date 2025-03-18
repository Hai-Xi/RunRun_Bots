package com.test.runrunbots.repository;

import com.test.runrunbots.model.Payment;
import com.test.runrunbots.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByOrderId(Long orderId);
    Optional<Payment> findByOrderIdAndStatus(Long orderId, PaymentStatus status);
}  