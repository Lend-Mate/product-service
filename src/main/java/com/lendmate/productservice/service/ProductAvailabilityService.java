package com.lendmate.productservice.service;

import com.lendmate.productservice.dto.requestDto.ProductAvailabilityRequest;
import com.lendmate.productservice.dto.responseDto.ProductAvailabilityResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductAvailabilityService {
    List<ProductAvailabilityResponse> createProductAvailabilityRecord(ProductAvailabilityRequest request);
    void deleteProductAvailabilityRecord(Long id);
    ProductAvailabilityResponse getProductAvailabilities(long productId);
    List<ProductAvailabilityResponse> getAllProductAvailabilities();
    int processExpiredRentedProducts(LocalDateTime date);
}
