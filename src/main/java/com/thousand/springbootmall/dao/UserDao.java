package com.thousand.springbootmall.dao;

import com.thousand.springbootmall.dto.UserRegisterRequest;
import com.thousand.springbootmall.model.User;

public interface UserDao {

    User getUserById(Integer userId);

    User getUserByEmail(String email);

    Integer createUser(UserRegisterRequest userRegisterRequest);

}
