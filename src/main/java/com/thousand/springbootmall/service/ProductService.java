package com.thousand.springbootmall.service;

import com.thousand.springbootmall.dto.ProductRequest;
import com.thousand.springbootmall.model.Product;

public interface ProductService {
    Product getProductById (Integer productId);

    Integer createProduct (ProductRequest productRequest);
}
