package com.project.lendmate.service;

import com.project.lendmate.dto.requestDto.ProductAvailabilityRequest;
import com.project.lendmate.dto.responseDto.ProductAvailabilityResponse;
import com.project.lendmate.dto.responseDto.ProductImageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ProductAvailabilityService {
    ProductAvailabilityResponse createProductAvailabilityRecord(ProductAvailabilityRequest request);
}
