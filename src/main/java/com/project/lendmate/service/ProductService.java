package com.project.lendmate.service;

import com.project.lendmate.dto.requestDto.ProductFilterRequest;
import com.project.lendmate.dto.requestDto.ProductRequest;
import com.project.lendmate.dto.requestDto.ProductSearchFilterRequest;
import com.project.lendmate.dto.responseDto.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    ProductResponse getProductById(Long id);
    Page<ProductResponse> getAllProducts(int page, int size, String sortBy, boolean ascending, ProductFilterRequest filter);
    ProductResponse updateProduct(Long id, ProductRequest productRequest);
    ProductResponse createProduct(ProductRequest productRequest);
    void deleteProduct(Long productId);
    void deleteProductsByOwner(Long ownerId);
    List<ProductResponse> getProductsByIds(List<Long> ids);
    Page<Long> searchProductsByIds(ProductSearchFilterRequest filter, String query, int page, int size, String sortBy, boolean ascending);
    List<String> getUniqueBrands(ProductFilterRequest filter);
}
