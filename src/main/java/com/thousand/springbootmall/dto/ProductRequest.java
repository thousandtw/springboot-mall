package com.thousand.springbootmall.dto;

import com.thousand.springbootmall.constant.ProductCategory;

import javax.validation.constraints.NotNull;

//dto (Data Transfer Object) (放雜項)
//定義創建商品時,前端需要傳哪些資訊過來
//@NotNull驗證傳入值
public class ProductRequest {
    //private Integer productId; (資料庫自動生成)
    @NotNull
    private String productName;
    @NotNull
    private ProductCategory category;
    @NotNull
    private String imageUrl;
    @NotNull
    private Integer price;
    @NotNull
    private Integer stock;

    private String description;
    //private Date createdDate; (由程式設定)
    //private Date lastModifiedDate; (由程式設定)

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
