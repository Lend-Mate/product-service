package com.lendmate.productservice.service;

import com.lendmate.productservice.dto.responseDto.ProductImageResponse;

import java.util.List;

public interface ProductImageService {
    List<ProductImageResponse> createProductImage(Long productId, List<String> imageNames);
    void deleteProductImages(List<Long> imageIds);
}
