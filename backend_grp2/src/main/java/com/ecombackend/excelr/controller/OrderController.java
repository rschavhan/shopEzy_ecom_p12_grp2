package com.ecombackend.excelr.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecombackend.excelr.dto.OrderDetailsDTO;
import com.ecombackend.excelr.model.Order;
import com.ecombackend.excelr.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    


    // Admin APIs
    
    @GetMapping
    public ResponseEntity<List<OrderDetailsDTO>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderDetailsDTO> orderDetails = orders.stream()
            .map(orderService::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(orderDetails);
    }



    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(
        @PathVariable Long orderId,
        @RequestParam String status
    ) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order) {
        order.setId(id);
        return orderService.saveOrder(order);
    }
//
//    @DeleteMapping("/admin/{id}")
//    public void deleteOrder(@PathVariable Long id) {
//        orderService.deleteOrder(id);
//    }

    // User APIs

//    @GetMapping("/user/{userId}")
//    public List<Order> getOrdersByUserId(@PathVariable Long userId) {
//        return orderService.getOrdersByUserId(userId);
//    }
    @PostMapping("/user/{userId}")
    public ResponseEntity<HttpStatus> createOrderForUser(@PathVariable Long userId, @RequestBody Order order) {
        Order createdOrder = orderService.saveOrderForUser(userId, order);
//        OrderDetailsDTO orderDTO = orderService.convertToDTO(createdOrder);
        
        

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/user/{userId}/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatusForUser(
        @PathVariable Long userId,
        @PathVariable Long orderId,
        @RequestParam String status
    ) {
        Order updatedOrder = orderService.updateOrderStatusForUser(userId, orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/user/{userId}/{orderId}")
    public ResponseEntity<Void> cancelOrderForUser(
        @PathVariable Long userId,
        @PathVariable Long orderId
    ) {
        orderService.cancelOrderForUser(userId, orderId);
        return ResponseEntity.noContent().build();
    }
    
    
    @GetMapping("/user/{userId}/details")
    public ResponseEntity<List<OrderDetailsDTO>> getOrdersByUserId(@PathVariable Long userId) {
        List<OrderDetailsDTO> orderDetails = orderService.getOrderDetailsByUserId(userId);
        return ResponseEntity.ok(orderDetails);
    }

    
    
}