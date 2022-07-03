package com.thousand.springbootmall.service.impl;

import com.thousand.springbootmall.dao.OrderDao;
import com.thousand.springbootmall.dao.ProductDao;
import com.thousand.springbootmall.dao.UserDao;
import com.thousand.springbootmall.dto.BuyItem;
import com.thousand.springbootmall.dto.CreateOrderRequest;
import com.thousand.springbootmall.model.Order;
import com.thousand.springbootmall.model.OrderItem;
import com.thousand.springbootmall.model.Product;
import com.thousand.springbootmall.model.User;
import com.thousand.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

//server層處理複雜的業務邏輯
@Component
public class OrderServiceImpl implements OrderService {

    private  final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private  UserDao userDao;

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

        //檢查user是否存在
        User user = userDao.getUserById(userId);
        if(user==null){
            log.warn("該userId{}不存在",userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount=0;
        List<OrderItem> orderItemList =new ArrayList<>();

        for(BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productDao.getProductById(buyItem.getProductId());

            //檢查product是否存在,庫存是否足夠
            if(product==null){
                log.warn("商品{}不存在",buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            else if (product.getStock()<buyItem.getQuantity()){
                log.warn("商品{}庫存不足,無法購買.剩餘庫存{}欲購買數量{}",buyItem.getProductId(),product.getStock(),buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            //扣除商品庫存
            productDao.updateStock(product.getProductId(),product.getStock()-buyItem.getQuantity());

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
