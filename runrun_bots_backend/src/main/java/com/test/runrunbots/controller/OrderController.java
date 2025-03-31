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
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Order>> createOrder(@AuthenticationPrincipal User user,
                                                          @Valid @RequestBody CreateOrderRequest request) {
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

    @PutMapping("/{orderId}/updateOrderStatus")
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

    /**
     * 获取所有 CREATED 状态的订单
     */
    @GetMapping("/created")
    public ResponseEntity<List<Order>> getCreatedOrders() {
        List<Order> orders = orderService.findCreatedOrders();
        return ResponseEntity.ok(orders);
    }

    /**
     * Updates can also be triggered via API in addition to scheduled tasks
     * 手动触发更新 CREATED->PENDING 的操作
     */
    @PostMapping("/update-to-pending")
    public ResponseEntity<Map<String, Object>> updateOrdersToPending() {
        int updatedCount = orderService.updateCreatedOrdersToPending();

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "成功将订单状态从 CREATED 更新为 PENDING");
        response.put("updatedCount", updatedCount);

        return ResponseEntity.ok(response);
    }

    /**
     * 手动触发更新已支付订单的状态
     */
    @PostMapping("/update-status")
    public ResponseEntity<String> updatePaidOrdersStatus() {
        int updatedCount = orderService.updatePaidOrdersToInProgress();
        return ResponseEntity.ok("成功更新 " + updatedCount + " 个订单状态为处理中");
    }

    /**
     * 批量将所有 IN_PROGRESS 订单标记为已送达
     */
    @PostMapping("/mark-delivered")
    public ResponseEntity<Map<String, Object>> markOrdersAsDelivered() {
        int updatedCount = orderService.markOrdersAsDelivered();
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "订单状态已更新, 批量将所有 IN_PROGRESS 订单标记为已送达",
                "updatedCount", updatedCount
        ));
    }

}  