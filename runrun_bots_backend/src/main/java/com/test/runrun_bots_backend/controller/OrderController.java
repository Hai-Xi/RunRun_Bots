package com.test.runrun_bots_backend.controller;

import com.test.runrun_bots_backend.model.dto.order.CreateOrderRequest;
import com.test.runrun_bots_backend.model.dto.order.OrderDTO;
import com.test.runrun_bots_backend.model.dto.order.TrackingInfo;
import com.test.runrun_bots_backend.model.dto.order.UpdateOrderStatusRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {  
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody CreateOrderRequest request) {
        // 创建订单逻辑  
        return null;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long orderId) {
        // 获取订单详情逻辑  
        return null;
    }

    @PutMapping("/{orderId}/status")  
    public ResponseEntity<OrderDTO> updateOrderStatus(  
            @PathVariable Long orderId,  
            @RequestBody UpdateOrderStatusRequest request) {
        // 更新订单状态逻辑  
        return null;
    }

    @GetMapping("/{orderId}/tracking")  
    public ResponseEntity<TrackingInfo> getTrackingInfo(@PathVariable Long orderId) {
        // 获取订单追踪信息逻辑  
        return null;
    }
}  