package com.test.runrunbots.controller;

import com.test.runrunbots.model.dto.order.CreateOrderRequest;
import com.test.runrunbots.model.dto.order.OrderDTO;
import com.test.runrunbots.model.dto.order.TrackingInfo;
import com.test.runrunbots.model.dto.order.UpdateOrderStatusRequest;
import com.test.runrunbots.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody CreateOrderRequest request) {
        // 创建订单逻辑  
        OrderDTO createdOrder = orderService.createOrder(request);
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long orderId) {
        // 获取订单详情逻辑  
        OrderDTO order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{orderId}/status")  
    public ResponseEntity<OrderDTO> updateOrderStatus(  
            @PathVariable Long orderId,  
            @RequestBody UpdateOrderStatusRequest request) {
        // 更新订单状态逻辑  
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