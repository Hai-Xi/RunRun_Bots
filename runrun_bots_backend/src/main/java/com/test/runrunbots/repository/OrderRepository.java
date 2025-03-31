package com.test.runrunbots.repository;

import com.test.runrunbots.model.Order;
import com.test.runrunbots.model.OrderStatus;
import com.test.runrunbots.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * 
     * Here, User_UserId tells Spring Data JPA to traverse the user relationship in the Order entity
     * and access the userId property of the User entity.
     *
     * @param userId
     * @return
     */
    List<Order> findByUser_UserId(Long userId);

    List<Order> findByStatus(OrderStatus status);

    @Query("SELECT o FROM Order o WHERE o.user.userId = :userId")
    List<Order> getOrderListByUserId(@Param("userId") Long userId);

    List<Order> findByUser(User user);

    Order findByOrderId(Long orderId);

    // 查询超过指定时间未完成支付的订单
    @Query("SELECT o FROM Order o WHERE o.status = 'PENDING' AND o.createdAt <= :expirationTime")
    List<Order> findExpiredOrders(LocalDateTime expirationTime);

    // 批量更新订单状态
    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.status = :CANCELLED WHERE o.orderId IN :expiredOrderIds")
    int updateOrderStatus(List<Long> expiredOrderIds, OrderStatus CANCELLED);
}