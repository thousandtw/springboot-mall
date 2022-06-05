package com.thousand.springbootmall.controller;

import com.thousand.springbootmall.dto.UserLoginRequest;
import com.thousand.springbootmall.dto.UserRegisterRequest;
import com.thousand.springbootmall.model.User;
import com.thousand.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    //新增
    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {

        //新增帳號(因方法中有創建帳號&查詢帳號,所以命名為register,而不是直接為createUser)
        Integer userId = userService.register(userRegisterRequest);
        //取得帳號
        User user = userService.getUserById(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    //登入
    @PostMapping("/users/login")
    public  ResponseEntity<User> Login(@RequestBody @Valid UserLoginRequest userLoginRequest){

        User user=userService.login(userLoginRequest);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
