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

    /**
     *
     * @Modifying注解告诉Spring Data JPA这是一个更新或删除操作，而不是一个查询操作。它使得方法可以执行更新操作，如UPDATE、DELETE等操作。
     * 如果没有@Modifying注解，Spring Data JPA会假设这是一个查询操作，而不是更新操作，因此会抛出异常。
     *
     *
     * @Transactional注解用于声明事务边界。在进行数据库更新、删除等写操作时，需要将这些操作放在事务中以确保数据一致性和完整性。
     * 如果没有@Transactional注解，当我们使用@Modifying执行更新操作时，会抛出异常：javax.persistence.TransactionRequiredException: Executing an update/delete query。
     *
     * 使用 JPQL（Java Persistence Query Language）查询，冒号（:）用于标识命名参数（Named Parameters）。这是 JPQL 的特殊语法，用于将方法的参数值绑定到查询字符串
     *
     * @param expiredOrderIds
     * @param CANCELLED
     * @return
     */
    // 批量更新订单状态
    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.status = :CANCELLED WHERE o.orderId IN :expiredOrderIds")
    int updateOrderStatus(List<Long> expiredOrderIds, OrderStatus CANCELLED);
}