package com.lendmate.productservice.service.impl;

import com.lendmate.productservice.dto.requestDto.ProductAttributeRequest;
import com.lendmate.productservice.dto.responseDto.ProductAttributeResponse;
import com.lendmate.productservice.mapper.ProductAttributeMapper;
import com.lendmate.productservice.model.ProductAttribute;
import com.lendmate.productservice.repository.ProductAttributeRepository;
import com.lendmate.productservice.service.ProductAttributeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProductAttributeServiceImpl implements ProductAttributeService {
    private final ProductAttributeMapper productAttributeMapper;
    private final ProductAttributeRepository productAttributeRepository;

    @Override
    public List<ProductAttributeResponse> createProductAttributes(Long productId, List<ProductAttributeRequest> attributes) {
        attributes.forEach(a -> {
            a.setProductId(productId);
            a.setCreatedAt(LocalDateTime.now());
        });

        List<ProductAttribute> productAttributes = attributes.stream().map(productAttributeMapper::toEntity).toList();
        List<ProductAttribute> savedAttributes = productAttributeRepository.saveAll(productAttributes);
        return savedAttributes.stream().map(productAttributeMapper::toResponseDto).toList();
    }

    @Override
    public void deleteProductAttribute(Long attributeId) {
        productAttributeRepository.deleteById(attributeId);
    }
}
