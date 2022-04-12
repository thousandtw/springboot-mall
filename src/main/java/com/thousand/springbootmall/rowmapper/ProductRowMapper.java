package com.thousand.springbootmall.rowmapper;

import com.thousand.springbootmall.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

//轉換數據的RowMapper,將資料庫取出的數據轉換成Java Object
//實作spring.jdbc的RowMapper(後加入<Product>泛型,表示轉換Product這個Java Object)
    public class ProductRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
    Product product = new Product(); //透過resultSet取出資料,並放入product

    product.setProductId(resultSet.getInt("product_id"));
    product.setProductName(resultSet.getString("product_name"));
    product.setCategory(resultSet.getString("category"));
    product.setImageUrl(resultSet.getString("image_url"));
    product.setPrice(resultSet.getInt("price"));
    product.setStock(resultSet.getInt("stock"));
    product.setDescription(resultSet.getString("description"));
    product.setCreatedDate(resultSet.getTimestamp("created_date"));
    product.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));

    return product;
    }

}
