package com.project.lendmate.service;

import com.project.lendmate.dto.requestDto.ProductAvailabilityRequest;
import com.project.lendmate.dto.responseDto.ProductAvailabilityResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductAvailabilityService {
    List<ProductAvailabilityResponse> createProductAvailabilityRecord(ProductAvailabilityRequest request);
    void deleteProductAvailabilityRecord(Long id);
    ProductAvailabilityResponse getProductAvailabilities(long productId);
    List<ProductAvailabilityResponse> getAllProductAvailabilities();
    int processExpiredRentedProducts(LocalDateTime date);
}
