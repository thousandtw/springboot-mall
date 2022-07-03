package com.thousand.springbootmall.rowmapper;

import com.thousand.springbootmall.model.OrderItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

//轉換數據的RowMapper,將資料庫取出的數據轉換成Java Object
//實作spring.jdbc的RowMapper(後加入<OrderItem>泛型,表示轉換OrderItem這個Java Object)
public class OrderItemRowMapper implements RowMapper<OrderItem> {


    @Override
    public OrderItem mapRow(ResultSet resultSet, int i) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderItemId(resultSet.getInt("order_item_id"));
        orderItem.setOrderId(resultSet.getInt("order_id"));
        orderItem.setProductId(resultSet.getInt("product_id"));
        orderItem.setQuantity(resultSet.getInt("quantity"));
        orderItem.setAmount(resultSet.getInt("amount"));

        //JOIN多張表,可擴充可另增
        orderItem.setProductName(resultSet.getString("product_name"));
        orderItem.setImageUrl(resultSet.getString("image_url"));

        return orderItem;
    }

}
