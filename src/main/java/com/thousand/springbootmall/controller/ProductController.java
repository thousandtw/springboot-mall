package com.thousand.springbootmall.controller;

import com.thousand.springbootmall.dto.ProductRequest;
import com.thousand.springbootmall.model.Product;
import com.thousand.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//創建Api,讓前端透過springboot程式取得商品資訊
@RestController //Controller層的Bean
public class ProductController {

    @Autowired
    private ProductService productService;

    //查詢商品功能
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

    //新增商品功能
    @PostMapping("products")                     //表示要接住前端傳來的json參數 //@Valid使@NotNull生效
    public ResponseEntity<Product> createProduct (@RequestBody @Valid ProductRequest productRequest){
        Integer productId = productService.createProduct(productRequest);

        Product product =productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    //修改商品功能
    @PutMapping("products/{productId}")
    public  ResponseEntity<Product> updateProduct(@PathVariable Integer productId,@RequestBody @Valid ProductRequest productRequest){

        //檢查 Product 是否存在
            Product product =productService.getProductById(productId);
        if(product==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        //修改商品數據
        productService.updateProduct(productId,productRequest);
        Product updateProduct=productService.getProductById(productId);
        return  ResponseEntity.status(HttpStatus.OK).body(updateProduct);
    }
}
