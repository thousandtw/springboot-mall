package com.thousand.springbootmall.service.impl;

import com.thousand.springbootmall.dao.OrderDao;
import com.thousand.springbootmall.dao.ProductDao;
import com.thousand.springbootmall.dto.BuyItem;
import com.thousand.springbootmall.dto.CreateOrderRequest;
import com.thousand.springbootmall.model.Order;
import com.thousand.springbootmall.model.OrderItem;
import com.thousand.springbootmall.model.Product;
import com.thousand.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//server層處理複雜的業務邏輯
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Override
    public Order getOrderById(Integer orderId) {
        //取得訂單
        Order order = orderDao.getOrderById(orderId);

        //取得訂單明細
        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);

        //合併 Order與 OrderItem
        order.setOrderItemList(orderItemList);
        return order;
    }

    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        int totalAmount=0;
        List<OrderItem> orderItemList =new ArrayList<>();

        for(BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productDao.getProductById(buyItem.getProductId());

            //計算總價錢
            int amount =buyItem.getQuantity() * product.getPrice();
            totalAmount = totalAmount + amount;

            //轉換 buyItem to orderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }

        //創建訂單
       Integer orderId = orderDao.createOrder(userId,totalAmount);
       orderDao.createOrderItems(orderId,orderItemList);
       return orderId;
    }
}
