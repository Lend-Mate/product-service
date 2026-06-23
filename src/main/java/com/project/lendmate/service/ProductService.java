package com.project.lendmate.service;

import com.project.lendmate.dto.requestDto.ProductRequest;
import com.project.lendmate.dto.responseDto.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    ProductResponse getProductById(Long id);
    Page<ProductResponse> getAllProducts(int page, int size, String sortBy, boolean ascending);
    ProductResponse updateProduct(Long id, ProductRequest productRequest);
    ProductResponse createProduct(ProductRequest productRequest);
    void deleteProduct(Long productId);
    void deleteProductsByOwner(Long ownerId);
    Page<ProductResponse> getProductsByIds(List<Long> ids, int page, int size, String sortBy, boolean ascending);
    List<ProductResponse> getProductsByIds(List<Long> ids);
    List<Long> searchProductsByIds(String query);
    Page<ProductResponse> searchProductsPostgres(String query, int page, int size, String sortBy, boolean ascending);
}
