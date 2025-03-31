package com.test.runrunbots.schedule;

import com.test.runrunbots.model.Order;
import com.test.runrunbots.model.OrderStatus;
import com.test.runrunbots.repository.OrderRepository;
import com.test.runrunbots.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 *
 * @Scheduled 注解实现定时任务检查和订单状态更新的逻辑。
 *
 */
@Component
public class OrderStatusScheduler {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    public OrderStatusScheduler(OrderRepository orderRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    //    @Autowired
    //    private OrderRepository orderRepository;


    /**
     * 定时任务：每1分钟执行一次，将 CREATED 订单更新为 PENDING
     */
    @Scheduled(cron = "0 */1 * * * *") // 每1分钟执行一次
    public void updateCreatedOrdersToPending() {
        System.out.println("开始执行订单状态更新任务：CREATED -> PENDING");
        int updatedCount = orderService.updateCreatedOrdersToPending();
        System.out.println("订单状态更新任务完成，共更新 " + updatedCount + " 个订单");
    }

    /**
     * 每分钟执行一次，检查并更新超时订单状态。
     * Cron 表达式：秒 分 时 日 月 周
     * 这里设置为每分钟执行一次 (* * * * *)
     */
    @Scheduled(cron = "0 */1 * * * *") // 每分钟执行一次
    public void updateExpiredOrdersStatus() {
        // 计算订单超时时间，例如付款超时为 15 分钟
        LocalDateTime fifteenMinutesAgo = LocalDateTime.now().minusMinutes(2);
        // LocalDateTime 转换为 java.util.Date
        Date expirationTime = Date.from(fifteenMinutesAgo.atZone(ZoneId.systemDefault()).toInstant());

        // 查询所有超时未支付的订单
        List<Order> expiredOrders = orderRepository.findExpiredOrders(fifteenMinutesAgo);
        if (expiredOrders.isEmpty()) {
            System.out.println("无需要更新的超时订单");
            return;
        }

        // 获取需要更新状态的订单 ID 列表
        List<Long> expiredOrderIds = expiredOrders.stream().map(Order::getOrderId).collect(Collectors.toList());

        // 批量更新订单状态为 CANCELLED（取消）
        int updatedCount = orderRepository.updateOrderStatus(expiredOrderIds, OrderStatus.CANCELLED);

        // 打印日志（可以记录到日志系统中）
        System.out.println("成功更新了 " + updatedCount + " 个超时订单为 CANCELLED");
    }
}