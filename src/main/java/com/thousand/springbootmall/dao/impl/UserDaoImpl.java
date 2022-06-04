package com.thousand.springbootmall.dao.impl;

import com.thousand.springbootmall.dao.UserDao;
import com.thousand.springbootmall.dto.UserRegisterRequest;
import com.thousand.springbootmall.model.User;
import com.thousand.springbootmall.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDaoImpl implements UserDao {

@Autowired
private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public User getUserById(Integer userId) {

      String sql="SELECT user_id, email, password,  created_date, last_modified_date " +
                      "FROM mall.user WHERE user_id=:userId";

      Map<String,Object>map=new HashMap<>();
      map.put("userId",userId);

       List <User> userList= namedParameterJdbcTemplate.query(sql,map,new UserRowMapper());

       if (userList.size()>0){
           return userList.get(0);
       }
       else {
           return null;
       }

    }

    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest) {

        //27
        String sql="INSERT INTO mall.user (email, password, created_date, last_modified_date) " +
                "VALUE (:email, :password, :createdDate, :lastModifiedDate) ";


        Map<String,Object> map =new HashMap<>();
        map.put("email",userRegisterRequest.getEmail());
        map.put("password",userRegisterRequest.getPassword());

        Date now =new Date();
        map.put("createdDate",now);
        map.put("lastModifiedDate",now);

        KeyHolder keyHolder= new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);

        int userId =keyHolder.getKey().intValue();
        return userId;
    }

}