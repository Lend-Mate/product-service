package com.project.lendmate.service;

import com.project.lendmate.dto.requestDto.ProductAvailabilityRequest;
import com.project.lendmate.dto.responseDto.ProductAvailabilityResponse;

import java.util.List;

public interface ProductAvailabilityService {
    ProductAvailabilityResponse createProductAvailabilityRecord(ProductAvailabilityRequest request);
    ProductAvailabilityResponse getProductAvailabilities(long productId);
    List<ProductAvailabilityResponse> getAllProductAvailabilities();
}
