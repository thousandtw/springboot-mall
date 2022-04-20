package com.thousand.springbootmall.dao;

import com.thousand.springbootmall.dto.ProductQueryParams;
import com.thousand.springbootmall.dto.ProductRequest;
import com.thousand.springbootmall.model.Product;

import java.util.List;

//使用NamedParameterJdbcTemplate從資料庫中取的Product的資料
public interface ProductDao {

    Integer countProduct(ProductQueryParams productQueryParams);

    List <Product> getProducts(ProductQueryParams productQueryParams);

    Product getProductById (Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId,ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
