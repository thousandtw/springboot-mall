package com.thousand.springbootmall.dao.impl;

import com.thousand.springbootmall.dao.ProductDao;
import com.thousand.springbootmall.model.Product;
import com.thousand.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

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
}
