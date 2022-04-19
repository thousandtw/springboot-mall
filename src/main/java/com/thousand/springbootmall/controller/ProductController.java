package com.thousand.springbootmall.controller;

import com.thousand.springbootmall.constant.ProductCategory;
import com.thousand.springbootmall.dto.ProductQueryParams;
import com.thousand.springbootmall.dto.ProductRequest;
import com.thousand.springbootmall.model.Product;
import com.thousand.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//創建Api,讓前端透過springboot程式取得商品資訊
@RestController //Controller層的Bean
public class ProductController {

    @Autowired
    private ProductService productService;

    //查詢商品列表功能
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(
            //表示 category 為 Url中取得的參數 //category參數表示前端透過參數可查看商品分類
                         //required為參數是否為必選 //search參數表示前端透過參數使用關鍵字
                                    //查詢條件
             @RequestParam(required = false) ProductCategory category
            ,@RequestParam(required = false) String search

                                    //排序
            ,@RequestParam(defaultValue = "created_date") String orderBy
            ,@RequestParam(defaultValue = "desc") String sort
                         //defaultValue為預設值,如果前端未給此參數,以預設為主
            ){
        //將 getProducts方法參數值統一歸納於ProductQueryParams-class(方便閱讀管理)
        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);

        List <Product> ProductList = productService.getProducts(productQueryParams);
        return ResponseEntity.status(HttpStatus.OK).body(ProductList);
    }

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
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,@RequestBody @Valid ProductRequest productRequest){

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

    //刪除商品功能
    @DeleteMapping("products/{productId}")
    public ResponseEntity<?> deleteProductById(@PathVariable Integer productId){
        productService.deleteProductById(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
