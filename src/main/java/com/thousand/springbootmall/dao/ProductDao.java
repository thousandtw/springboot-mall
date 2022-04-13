package com.thousand.springbootmall.dao;

import com.thousand.springbootmall.dto.ProductRequest;
import com.thousand.springbootmall.model.Product;

//使用NamedParameterJdbcTemplate從資料庫中取的Product的資料
public interface ProductDao {
    Product getProductById (Integer productId);

    Integer createProduct(ProductRequest productRequest);
}
