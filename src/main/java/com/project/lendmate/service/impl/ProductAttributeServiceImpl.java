package com.project.lendmate.service.impl;

import com.project.lendmate.dto.requestDto.ProductAttributeRequest;
import com.project.lendmate.dto.responseDto.ProductAttributeResponse;
import com.project.lendmate.mapper.ProductAttributeMapper;
import com.project.lendmate.model.ProductAttribute;
import com.project.lendmate.repository.ProductAttributeRepository;
import com.project.lendmate.service.ProductAttributeService;
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
