package com.lendmate.productservice.service;

import com.lendmate.productservice.dto.requestDto.ProductFilterRequest;
import com.lendmate.productservice.dto.requestDto.ProductRequest;
import com.lendmate.productservice.dto.requestDto.ProductSearchFilterRequest;
import com.lendmate.productservice.dto.responseDto.ProductResponse;
import com.lendmate.productservice.event.StockDecreaseItem;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

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
    Map<Long, Integer> getProductQuantities(List<Long> ids);
    void decreaseStockForItems(List<StockDecreaseItem> items, Long orderId);
    boolean existsProduct(Long productId);
}
