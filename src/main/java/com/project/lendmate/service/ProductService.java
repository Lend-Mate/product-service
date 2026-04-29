package com.project.lendmate.service;

import com.project.lendmate.dto.request.ProductRequest;
import com.project.lendmate.dto.response.ProductResponse;
import java.util.List;

public interface ProductService {
    ProductResponse getProductById(Long id);
    List<ProductResponse> getAllProducts();
    void updateProduct(ProductRequest productRequest);
    ProductResponse createProduct(ProductRequest productRequest);
    void deleteProduct(Long productId);
}
