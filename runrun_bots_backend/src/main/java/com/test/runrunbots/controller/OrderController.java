package com.test.runrunbots.controller;

import com.alibaba.fastjson2.JSON;
import com.test.runrunbots.model.Order;
import com.test.runrunbots.model.User;
import com.test.runrunbots.model.dto.order.CreateOrderRequest;
import com.test.runrunbots.model.dto.order.OrderDTO;
import com.test.runrunbots.model.dto.order.TrackingInfo;
import com.test.runrunbots.model.dto.order.UpdateOrderStatusRequest;
import com.test.runrunbots.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@AuthenticationPrincipal User user,
                                             @RequestBody CreateOrderRequest request) {
        // Create Order Logic
        log.info("@AuthenticationPrincipal user: {}", JSON.toJSONString(user));
        Order createdOrder = orderService.createOrder(user,request);
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping("/orderList/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrderList(@AuthenticationPrincipal User user,
                                                       @PathVariable Long userId) {
        // Logic for Retrieving Order List Details
        return null;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long orderId) {
        // Logic for Retrieving Order Details
        OrderDTO order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{orderId}/status")  
    public ResponseEntity<OrderDTO> updateOrderStatus(  
            @PathVariable Long orderId,  
            @RequestBody UpdateOrderStatusRequest request) {
        // Update Order Status Logic
        OrderDTO updatedOrder = orderService.updateOrderStatus(orderId, request);
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping("/{orderId}/tracking")  
    public ResponseEntity<TrackingInfo> getTrackingInfo(@PathVariable Long orderId) {
        // 获取订单追踪信息逻辑  
        TrackingInfo trackingInfo = orderService.getTrackingInfo(orderId);
        return ResponseEntity.ok(trackingInfo);
    }
}  