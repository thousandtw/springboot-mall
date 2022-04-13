package com.thousand.springbootmall.dao.impl;

import com.thousand.springbootmall.dao.ProductDao;
import com.thousand.springbootmall.dto.ProductRequest;
import com.thousand.springbootmall.model.Product;
import com.thousand.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//impl package用於將介面與實作的類別分開存放
//成為bean,並實作ProductDao介面,與資料庫溝通
@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired //注入NamedParameterJdbcTemplate這個bean
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer productId) {

        String sql ="SELECT product_id, product_name, category, image_url, price, stock, description, created_date, last_modified_date FROM product WHERE product_id= :productId";

        Map<String,Object> map =new HashMap<>();
        map.put("productId",productId);
        //反白query按萬用鍵,再選introduce local variable 自動生成對應變數 productList
        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if(productList.size()>0){
            return productList.get(0);
        }
        else {
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {

       String sql ="INSERT INTO product(product_name, category, image_url, price, stock, description, created_date, last_modified_date)" +
               "VALUES ( :productName, :category, :imageUrl, :price, :stock, :description, :createdDate, :lastModifiedDate)";

        Map<String,Object> map =new HashMap<>();
        map.put("productName",productRequest.getProductName());
        map.put("category",productRequest.getCategory().toString()); //將自訂義的 enum ProductCategory轉換成資料庫規定的字串
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());

        //由程式給予紀錄當下時間 (當成商品創建 &修改時間)
        Date now = new Date();
        map.put("createdDate",now);
        map.put("lastModifiedDate",now);

        //儲存資料庫自動生成的productId
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);

        int productId = keyHolder.getKey().intValue();
        return productId;
    }
}
