package com.thousand.springbootmall.rowmapper;

import com.thousand.springbootmall.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

//資料庫結果轉換成obj
public class UserRowMapper implements RowMapper<User> {

    public User mapRow(ResultSet resultSet,int i) throws SQLException{
        User user = new User();
        user.setUserid(resultSet.getInt("user_id"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setCreatedDate(resultSet.getTimestamp("created_date"));
        user.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));
        return user;
    }

}
