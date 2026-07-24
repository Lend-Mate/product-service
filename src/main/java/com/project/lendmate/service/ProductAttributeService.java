package com.project.lendmate.service;

import com.project.lendmate.dto.requestDto.ProductAttributeRequest;
import com.project.lendmate.dto.responseDto.ProductAttributeResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductAttributeService {
    List<ProductAttributeResponse> createProductAttributes(Long productId, List<ProductAttributeRequest> attributes);
    void deleteProductAttribute(Long id);
}
