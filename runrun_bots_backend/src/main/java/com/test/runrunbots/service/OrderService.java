package com.test.runrunbots.service;  

import com.alibaba.fastjson2.JSON;
import com.test.runrunbots.model.DeliveryMethod;
import com.test.runrunbots.model.Order;
import com.test.runrunbots.model.OrderStatus;
import com.test.runrunbots.model.User;
import com.test.runrunbots.model.dto.order.CreateOrderRequest;
import com.test.runrunbots.model.dto.order.OrderDTO;  
import com.test.runrunbots.model.dto.order.TrackingInfo;  
import com.test.runrunbots.model.dto.order.UpdateOrderStatusRequest;
import com.test.runrunbots.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public Order createOrder(User user, CreateOrderRequest request) {
        // 模拟创建订单逻辑  
        Order order = new Order();
//        order.setOrderId(1L);
        order.setUser(user);
        order.setItemDescription(request.getItemDescription());
        order.setPickupLocation(request.getPickupLocation());
        order.setDeliveryLocation(request.getDeliveryLocation());
        order.setDeliveryMethod(DeliveryMethod.valueOf(request.getDeliveryMethod()));
        order.setStatus(OrderStatus.valueOf("CREATED"));
        order.setCreatedAt(java.time.LocalDateTime.now());
        log.info("@AuthenticationPrincipal User user: {}", JSON.toJSONString(user));
        log.info("Order created: {}", order);
        return orderRepository.save(order);
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
}  