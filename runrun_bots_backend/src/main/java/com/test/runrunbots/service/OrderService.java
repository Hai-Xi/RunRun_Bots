package com.test.runrunbots.service;  

import com.alibaba.fastjson2.JSON;
import com.test.runrunbots.controller.OrderIdNotExistException;
import com.test.runrunbots.model.*;
import com.test.runrunbots.model.dto.order.*;
import com.test.runrunbots.repository.OrderRepository;
import com.test.runrunbots.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    public OrderService(OrderRepository orderRepository, PaymentRepository paymentRepository) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public Order createOrder(User user, CreateOrderRequest request) {
        // 模拟创建订单逻辑
        // 创建订单对象
        Order order = new Order();
        order.setUser(user);
        order.setItemDescription(request.getItemDescription());
        order.setPickupLocation(request.getPickupLocation());
        order.setDeliveryLocation(request.getDeliveryLocation());
        order.setDeliveryMethod(request.getDeliveryMethod());
        order.setStatus(OrderStatus.valueOf("CREATED"));
        order.setCreatedAt(LocalDateTime.now());

        // 创建支付对象
        Payment payment = new Payment();
        log.info("order.getOrderId(): {}", order.getOrderId());
        payment.setAmount(BigDecimal.valueOf(request.getTotalAmount()));
        payment.setPaymentMethod(PaymentMethod.valueOf(request.getPaymentMethod()));
        payment.setStatus(PaymentStatus.valueOf(request.getPaymentStatus()));
        payment.setOrder(order); // 设置关联的订单

        // 设置订单中的支付对象
        // 设置双向关联
        // 在使用双向关系时，需要特别注意保证关系的一致性。通常，需要在设置关联关系时手动维护两个实体之间的双向关联。
        log.info("payment created: {}", payment);
        order.setPayment(payment);  // order 端维护，同时设置 payment 的 order 关系

        // 持久化订单和支付记录
        log.info("@AuthenticationPrincipal User user: {}", JSON.toJSONString(user));
        log.info("Order created: {}", order);
        return orderRepository.save(order);  // 当调用 orderRepository.save(order) 时，因为 CascadeType.ALL，Hibernate 会级联保存 Payment 支付记录。
    }  

    public OrderDTO getOrderById(Long orderId) throws OrderIdNotExistException {
        // 模拟获取订单逻辑  
        OrderDTO order = new OrderDTO();  
        order.setOrderId(orderId);  
        order.setUserId(123L);  
//        order.setProductIds(java.util.Arrays.asList(1L, 2L, 3L));
//        order.setShippingAddress("123 Test Street");
        order.setStatus("SHIPPED");  
        order.setCreatedAt(LocalDateTime.now());  
        return order;
    }  

    public OrderDTO updateOrderStatus(Long orderId, UpdateOrderStatusRequest request) {
        // 模拟更新订单状态逻辑  
        OrderDTO order = getOrderById(orderId);
        log.info("order.getOrderId(): {}", order.getOrderId());
        log.info("order.getStatus(): {}", order.getStatus());
        order.setStatus(request.getStatus());
        LocalDateTime now = LocalDateTime.now();
        order.setUpdatedAt(now);
        int success = orderRepository.updateOrderStatus(order.getStatus(),
                order.getUpdatedAt(),
                order.getOrderId());
        OrderDTO fail = null;
        return success == 1 ? order : fail;
    }  

    public TrackingInfo getTrackingInfo(Long orderId) {  
        // 模拟获取追踪信息逻辑  
        TrackingInfo trackingInfo = new TrackingInfo();  
        trackingInfo.setOrderId(orderId);
        trackingInfo.setTrackingNumber("TRACK123456");
        trackingInfo.setDeliveryMethod("ROBOT");
        trackingInfo.setCurrentStatus("In Transit");
        trackingInfo.setEstimatedArrivalTime("Now");
        return trackingInfo;  
    }

    public List<Order> findByUser_UserId(User user) {
        return orderRepository.findByUser_UserId(user.getUserId());
    }

    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    public Order getOrderByOrderId(Long orderId) throws OrderIdNotExistException {
        if (orderRepository.existsById(orderId)) {
            return orderRepository.findByOrderId(orderId);
        }
        throw new OrderIdNotExistException();
    }

    /**
     * 查询所有状态为 CREATED 的订单
     */
    public List<Order> findCreatedOrders() {
        return orderRepository.findOrdersByStatus(OrderStatus.CREATED);
    }

    /**
     * 将所有 CREATED 订单批量更新为 PENDING 状态
     * @return 更新的订单数量
     */
    @Transactional
    public int updateCreatedOrdersToPending() {
        // 1. 查询所有 CREATED 状态的订单
        List<Order> createdOrders = findCreatedOrders();

        if (createdOrders.isEmpty()) {
            System.out.println("没有找到状态为 CREATED 的订单，无需更新。");
            return 0;
        }

        // 2. 获取所有 CREATED 订单的 ID 列表
        List<Long> orderIds = createdOrders.stream()
                .map(Order::getOrderId)
                .collect(Collectors.toList());

        System.out.println("找到 " + orderIds.size() + " 个需要更新状态的订单。");

        // 3. 批量更新这些订单状态为 PENDING
        LocalDateTime now = LocalDateTime.now();
        int updatedCount = orderRepository.updateOrderStatusFromCreatedToPending(orderIds, OrderStatus.PENDING, now);

        System.out.println("成功更新 " + updatedCount + " 个订单状态为 PENDING。");

        return updatedCount;
    }

    /**
     * 将所有已支付的订单状态更新为处理中
     * @return 更新的订单数量
     */
    @Transactional
    public int updatePaidOrdersToInProgress() {
        // 1. 查询所有已支付的订单
        List<Order> paidOrders = orderRepository.findByStatus(OrderStatus.PAID);

        if (paidOrders.isEmpty()) {
            return 0; // 没有需要更新的订单
        }

        // 2. 获取需要更新状态的订单 ID 列表
        List<Long> paidOrderIds = paidOrders.stream()
                .map(Order::getOrderId)
                .collect(Collectors.toList());

        // 3. 批量更新订单状态为 IN_PROGRESS
        LocalDateTime now = LocalDateTime.now();
        return orderRepository.updateOrdersStatus(paidOrderIds, OrderStatus.IN_PROGRESS, now);
    }

    /**
     * 查询所有处理中的订单
     */
    public List<Order> getInProgressOrders() {
        return orderRepository.findOrdersByStatus(OrderStatus.IN_PROGRESS);
    }

    /**
     * 将处理中的订单批量更新为已送达状态
     * @return 更新的订单数量
     */
    @Transactional
    public int markOrdersAsDelivered() {
        // 1. 查询所有处理中的订单
        List<Order> inProgressOrders = getInProgressOrders();

        // 如果没有处理中的订单，直接返回0
        if (inProgressOrders.isEmpty()) {
            System.out.println("没有处理中的订单需要更新");
            return 0;
        }

        // 2. 获取订单ID列表
        List<Long> orderIds = inProgressOrders.stream()
                .map(Order::getOrderId)
                .collect(Collectors.toList());

        // 3. 批量更新订单状态为已送达
        LocalDateTime now = LocalDateTime.now();
        int updatedCount = orderRepository.updateOrdersStatus(orderIds, OrderStatus.DELIVERED, now);

        System.out.println("成功将 " + updatedCount + " 个订单标记为已送达");
        return updatedCount;
    }

}