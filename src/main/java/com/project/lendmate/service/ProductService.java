package com.project.lendmate.service;

import com.project.lendmate.dto.requestDto.ProductRequest;
import com.project.lendmate.dto.responseDto.ProductResponse;
import java.util.List;

public interface ProductService {
    ProductResponse getProductById(Long id);
    List<ProductResponse> getAllProducts();
    ProductResponse updateProduct(Long id, ProductRequest productRequest);
    ProductResponse createProduct(ProductRequest productRequest);
    void deleteProduct(Long productId);
    void deleteProductsByOwner(Long ownerId);
}
