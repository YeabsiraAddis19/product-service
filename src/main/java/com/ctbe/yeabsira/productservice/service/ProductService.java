package com.ctbe.yeabsira.productservice.service;

import com.ctbe.yeabsira.productservice.dto.ProductRequest;
import com.ctbe.yeabsira.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    ProductResponse getProductById(Long id);

    ProductResponse getProductBySku(String sku);

    List<ProductResponse> getAllProducts();

    List<ProductResponse> getProductsByCategory(String category);

    List<ProductResponse> getActiveProducts();

    List<ProductResponse> searchProductsByName(String name);

    ProductResponse updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);
}