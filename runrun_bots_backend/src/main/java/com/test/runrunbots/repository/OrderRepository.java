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

    @Query("SELECT o FROM Order o WHERE o.user.userId = :userId")
    List<Order> getOrderListByUserId(@Param("userId") Long userId);

    List<Order> findByUser(User user);

    Order findByOrderId(Long orderId);

    /**
     * 查询所有状态为 CREATED 的订单
     */
    @Query("SELECT o FROM Order o WHERE o.status = :status")
    List<Order> findOrdersByStatus(@Param("status") OrderStatus status);

    /**
     * 也可以使用方法名称派生查询
     * 与上面的自定义查询等效
     */
    // List<Order> findByStatus(OrderStatus status);

    /**
     * 批量更新订单状态
     * @param orderIds 需要更新的订单 ID 列表
     * @param newStatus 新的订单状态
     * @return 更新的记录数
     */
    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.status = :newStatus, o.updatedAt = :updateTime WHERE o.orderId IN :ids")
    int updateOrderStatusFromCreatedToPending(@Param("ids") List<Long> orderIds,
                                              @Param("newStatus") OrderStatus newStatus,
                                              @Param("updateTime") LocalDateTime updateTime);

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
     * 如果方法参数名称与查询中的命名参数名称完全相同，Spring Data JPA 会自动进行绑定
     * 当参数名称与查询中的命名参数不同时，必须使用 @Param 注解显式指定
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

    /**
     * 查询所有已支付的订单
     */
    List<Order> findByStatus(OrderStatus status);

    /**
     * 批量更新 PAID 订单状态为 IN_PROGRESS
     */
    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.status = :newStatus WHERE o.orderId IN :orderIds")
    int updateOrdersStatus(@Param("orderIds") List<Long> orderIds,
                           @Param("newStatus") OrderStatus newStatus);

    /**
     * 批量更新订单状态并更新修改时间
     */
    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.status = :newStatus, o.updatedAt = :updateTime WHERE o.orderId IN :ids")
    int updateOrdersStatus(
            @Param("ids") List<Long> orderIds,
            @Param("newStatus") OrderStatus newStatus,
            @Param("updateTime") LocalDateTime updateTime);

    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.status = :status, o.updatedAt = :updatedAt WHERE o.orderId = :orderId")
    int updateOrderStatus(@org.springframework.data.repository.query.Param("status") OrderStatus status,
                          @org.springframework.data.repository.query.Param("updatedAt") java.time.LocalDateTime updatedAt,
                          @org.springframework.data.repository.query.Param("orderId") Long orderId);

}