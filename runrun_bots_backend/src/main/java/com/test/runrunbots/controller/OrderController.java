package com.test.runrunbots.controller;

import com.alibaba.fastjson2.JSON;
import com.test.runrunbots.model.dto.unifiedGlobalResponse.ApiResponse;
import com.test.runrunbots.model.Order;
import com.test.runrunbots.model.User;
import com.test.runrunbots.model.dto.order.CreateOrderRequest;
import com.test.runrunbots.model.dto.order.OrderDTO;
import com.test.runrunbots.model.dto.order.TrackingInfo;
import com.test.runrunbots.model.dto.order.UpdateOrderStatusRequest;
import com.test.runrunbots.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ApiResponse<Order>> createOrder(@AuthenticationPrincipal User user,
                                                         @RequestBody CreateOrderRequest request) {
        // Create Order Logic
        log.info("@AuthenticationPrincipal user: {}", JSON.toJSONString(user));
        Order createdOrder = orderService.createOrder(user,request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(createdOrder));
    }

    @GetMapping("/orderList")
    public ResponseEntity<ApiResponse<List<Order>>> getOrdersByUser(@AuthenticationPrincipal User user) {
        // Logic for Retrieving Order List Details
        List<Order> orderListByUserId = orderService.getOrdersByUser(user);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(orderListByUserId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<Order>> getOrderByOrderId(@PathVariable Long orderId) {
        // Logic for Retrieving Order Details
        Order order = orderService.getOrderByOrderId(orderId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(order));
    }

    @PutMapping("/{orderId}/status")  
    public ResponseEntity<ApiResponse<OrderDTO>> updateOrderStatus(
            @PathVariable Long orderId,  
            @RequestBody UpdateOrderStatusRequest request) {
        // Update Order Status Logic
        OrderDTO updatedOrder = orderService.updateOrderStatus(orderId, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(updatedOrder));
    }

    @GetMapping("/{orderId}/tracking")  
    public ResponseEntity<TrackingInfo> getTrackingInfo(@PathVariable Long orderId) {
        // 获取订单追踪信息逻辑  
        TrackingInfo trackingInfo = orderService.getTrackingInfo(orderId);
        return ResponseEntity.ok(trackingInfo);
    }
}  