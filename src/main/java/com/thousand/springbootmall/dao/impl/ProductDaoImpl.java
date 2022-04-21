package com.thousand.springbootmall.dao.impl;

import com.thousand.springbootmall.dao.ProductDao;
import com.thousand.springbootmall.dto.ProductQueryParams;
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
    public Integer countProduct(ProductQueryParams productQueryParams) {
        String sql = "SELECT count(*) FROM product WHERE 1=1 ";

        Map<String, Object> map = new HashMap<>();

        //查詢條件的 SQL拼接
        sql = addFilteringSql(sql, map, productQueryParams);
        //當透過SQL取count時,常用queryForObject (第三個參數以照芳方法返回類型)
        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
        return total;
    }

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        String sql = "SELECT product_id,product_name,category,image_url,price,stock,description,created_date,last_modified_date " +
                "FROM product WHERE 1=1";
        //使靈活拼接sql語法 (1=1不會影響查詢條件)

        Map<String, Object> map = new HashMap<>();

        //查詢條件的 SQL拼接
        sql = addFilteringSql(sql, map, productQueryParams);

        //排序
        //ORDER BY不能使用 :SOL變數,只能使用字串拼接 //ORDER BY前後皆須含空格,變數連接需空白字串
        sql = sql + " ORDER BY " + productQueryParams.getOrderBy() + " " + productQueryParams.getSort();


        //分頁
        sql = sql + " LIMIT :limit OFFSET :offset";
        map.put("limit", productQueryParams.getLimit());
        map.put("offset", productQueryParams.getOffset());
        //將資料庫數據轉換成java object
        List<Product> ProductList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        return ProductList;
    }

    @Override
    public Product getProductById(Integer productId) {

        String sql = "SELECT product_id, product_name, category, image_url, price, stock, description, created_date, last_modified_date FROM product WHERE product_id= :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        //反白query按萬用鍵,再選introduce local variable 自動生成對應變數 productList
        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if (productList.size() > 0) {
            return productList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {

        String sql = "INSERT INTO product(product_name, category, image_url, price, stock, description, created_date, last_modified_date)" +
                "VALUES ( :productName, :category, :imageUrl, :price, :stock, :description, :createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString()); //將自訂義的 enum ProductCategory轉換成資料庫規定的字串
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        //由程式給予紀錄當下時間 (當成商品創建 &修改時間)
        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        //儲存資料庫自動生成的productId
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int productId = keyHolder.getKey().intValue();
        return productId;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {

        String sql = "UPDATE product SET product_name = :productName, category = :category, image_url = :imageUrl," +
                " price = :price, stock = :stock, description = :description, last_modified_date = :lastModifiedDate " +
                "WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        map.put("lastModifiedDate", new Date());

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteProductById(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id = :productId";
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        namedParameterJdbcTemplate.update(sql, map);
    }

    //透過查詢條件拼接 SQL
    private String addFilteringSql(String sql, Map<String, Object> map, ProductQueryParams productQueryParams) {

        //查詢條件
        //Dao層在實作 SQL時,切記加上驗證判斷
        if (productQueryParams.getCategory() != null) {
            //AND前'空白'非常重要 (使前方 SQL不相互黏上干擾) //類別搜尋
            sql = sql + " AND category= :category";
            map.put("category", productQueryParams.getCategory().name());
            //name方法將enum類型轉換成string
        }
        if (productQueryParams.getSearch() != null) {
            //關鍵字搜尋-LIKE模糊查詢
            sql = sql + " AND product_name LIKE :search";
            map.put("search", "%" + productQueryParams.getSearch() + "%");
            // LIKE同常搭配"%"~"%"(%為任意字符,放前後為商品名稱中包含)(%寫在map的值內)
        }
        return sql;
    }
}
