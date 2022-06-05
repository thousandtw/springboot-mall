package com.thousand.springbootmall.service.impl;

import com.thousand.springbootmall.dao.UserDao;
import com.thousand.springbootmall.dto.UserRegisterRequest;
import com.thousand.springbootmall.model.User;
import com.thousand.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    //創建 Log
    private  final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }


    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {

        //查詢帳號
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());
        if (user!=null){
            log.warn("此email {} 已經被註冊",userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //新增帳號
        return userDao.createUser(userRegisterRequest);
    }
}
