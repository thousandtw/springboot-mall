package com.thousand.springbootmall.dao;

import com.thousand.springbootmall.dto.UserRegisterRequest;
import com.thousand.springbootmall.model.User;

public interface UserDao {

    User getUserById(Integer userId);

    Integer createUser(UserRegisterRequest userRegisterRequest);

}
