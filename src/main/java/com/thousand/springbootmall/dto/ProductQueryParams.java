package com.thousand.springbootmall.dto;

import com.thousand.springbootmall.constant.ProductCategory;

//存放getProducts方法參數值
public class ProductQueryParams {

    private ProductCategory category;
   private String search;

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
