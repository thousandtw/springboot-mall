package  com.thousand.springbootmall.service;

import com.thousand.springbootmall.dto.CreateOrderRequest;

public interface OrderService {

   Integer createOrder (Integer userId, CreateOrderRequest createOrderRequest);
}
