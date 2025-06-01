package com.product.koptani.service;

import com.product.koptani.dto.ProductListResponse;
import com.product.koptani.dto.ProductRequest;
import com.product.koptani.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    // Get All Product
    List<ProductListResponse> productList();

    // Get By ID
    ProductResponse getProductById(Integer id);

    // Add Product
    ProductResponse addProduct(ProductRequest productRequest);

    // Update Product By ID and Request
    ProductResponse updateProduct(Integer id, ProductRequest productRequest);

    // Delete Product By ID
    String deleteProduct(Integer id);

    // Update Stock Product
    public String updateStockProduct(Integer id, Integer stock);
}
