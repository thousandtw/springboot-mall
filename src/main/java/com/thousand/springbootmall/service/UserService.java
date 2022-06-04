package com.thousand.springbootmall.service;

import com.thousand.springbootmall.dto.UserRegisterRequest;
import com.thousand.springbootmall.model.User;

public interface UserService {
    User getUserById(Integer userId);

    Integer register(UserRegisterRequest userRegisterRequest);
}
