package com.test.runrunbots.service;  

import com.alibaba.fastjson2.JSON;
import com.test.runrunbots.model.*;
import com.test.runrunbots.model.dto.order.CreateOrderRequest;
import com.test.runrunbots.model.dto.order.OrderDTO;  
import com.test.runrunbots.model.dto.order.TrackingInfo;  
import com.test.runrunbots.model.dto.order.UpdateOrderStatusRequest;
import com.test.runrunbots.repository.OrderRepository;
import com.test.runrunbots.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

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
        order.setDeliveryMethod(DeliveryMethod.valueOf(request.getDeliveryMethod()));
        order.setStatus(OrderStatus.valueOf("CREATED"));
        order.setCreatedAt(java.time.LocalDateTime.now());

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

    public OrderDTO getOrderById(Long orderId) {  
        // 模拟获取订单逻辑  
        OrderDTO order = new OrderDTO();  
        order.setOrderId(orderId);  
        order.setUserId(123L);  
//        order.setProductIds(java.util.Arrays.asList(1L, 2L, 3L));
//        order.setShippingAddress("123 Test Street");
        order.setStatus("SHIPPED");  
        order.setCreatedAt(java.time.LocalDateTime.now());  
        return order;  
    }  

    public OrderDTO updateOrderStatus(Long orderId, UpdateOrderStatusRequest request) {  
        // 模拟更新订单状态逻辑  
        OrderDTO order = getOrderById(orderId);  
        order.setStatus(request.getStatus());  
        return order;  
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
}