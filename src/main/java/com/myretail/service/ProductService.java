package com.myretail.service;

import com.myretail.exception.MyRetailException;
import com.myretail.model.Product;
import com.myretail.vo.ProductVo;
import org.json.JSONException;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> fetchAllProducts();
    Product insertProduct(Product product);
    ProductVo fetchProductDetailsById(int prodId);
    ProductVo fetchDataForID(int prodId) throws JSONException;
    boolean updateProductById(ProductVo product) throws MyRetailException;
}
