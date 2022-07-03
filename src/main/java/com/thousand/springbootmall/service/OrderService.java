package com.thousand.springbootmall.service;

import com.thousand.springbootmall.dto.CreateOrderRequest;
import com.thousand.springbootmall.dto.OrderQueryParams;
import com.thousand.springbootmall.model.Order;

import java.util.List;

public interface OrderService {
    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Order getOrderById(Integer orderId);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
