package com.thousand.springbootmall.controller;

import com.thousand.springbootmall.dto.CreateOrderRequest;
import com.thousand.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import com.thousand.springbootmall.model.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId ,
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest) {
        //創建訂單
       Integer orderId = orderService.createOrder(userId,createOrderRequest);

       //查詢訂單
       Order order = orderService.getOrderById(orderId);

       return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
