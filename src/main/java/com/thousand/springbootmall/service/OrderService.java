package  com.thousand.springbootmall.service;

import com.thousand.springbootmall.dto.CreateOrderRequest;
import com.thousand.springbootmall.model.Order;

public interface OrderService {

   Order getOrderById(Integer orderId);

   Integer createOrder (Integer userId, CreateOrderRequest createOrderRequest);
}
