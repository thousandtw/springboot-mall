package com.thousand.springbootmall.service;

import com.thousand.springbootmall.dto.ProductQueryParams;
import com.thousand.springbootmall.dto.ProductRequest;
import com.thousand.springbootmall.model.Product;

import java.util.List;

public interface ProductService {

    Integer countProduct(ProductQueryParams productQueryParams);

    List<Product> getProducts(ProductQueryParams productQueryParams);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
