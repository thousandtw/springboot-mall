package com.thousand.springbootmall.controller;

import com.thousand.springbootmall.model.Product;
import com.thousand.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

//創建Api,讓前端透過springboot程式取得商品資訊
@RestController //Controller層的Bean
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("products/{productId}")       //表示productId是從路徑取得的
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){

        Product product =productService.getProductById(productId);
        //判斷後給予前端回應
        if(product!=null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }
        else {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
