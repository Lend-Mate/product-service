package com.lendmate.productservice.service;

import com.lendmate.productservice.dto.requestDto.ProductAttributeRequest;
import com.lendmate.productservice.dto.responseDto.ProductAttributeResponse;

import java.util.List;

public interface ProductAttributeService {
    List<ProductAttributeResponse> createProductAttributes(Long productId, List<ProductAttributeRequest> attributes);
    void deleteProductAttribute(Long id);
}
