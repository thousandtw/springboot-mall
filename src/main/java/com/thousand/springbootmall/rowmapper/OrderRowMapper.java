package com.thousand.springbootmall.rowmapper;

import com.thousand.springbootmall.model.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

//轉換數據的RowMapper,將資料庫取出的數據轉換成Java Object
//實作spring.jdbc的RowMapper(後加入<Order>泛型,表示轉換Order這個Java Object)
public class OrderRowMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet resultSet, int i) throws SQLException {
        Order order = new Order();
        order.setOrderId(resultSet.getInt("order_Id"));
        order.setUserId(resultSet.getInt("user_id"));
        order.setTotalAmount(resultSet.getInt("total_amount"));
        order.setCreatedDate(resultSet.getTimestamp("created_date"));
        order.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));
        return order;
    }
}
