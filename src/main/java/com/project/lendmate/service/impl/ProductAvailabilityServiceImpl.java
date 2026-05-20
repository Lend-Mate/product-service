package com.project.lendmate.service.impl;

import com.project.lendmate.dto.requestDto.ProductAvailabilityRequest;
import com.project.lendmate.dto.responseDto.ProductAvailabilityResponse;
import com.project.lendmate.dto.responseDto.ProductImageResponse;
import com.project.lendmate.mapper.ProductAvailabilityMapper;
import com.project.lendmate.mapper.ProductImageMapper;
import com.project.lendmate.mapper.ProductMapper;
import com.project.lendmate.model.ProductAvailability;
import com.project.lendmate.model.ProductImage;
import com.project.lendmate.repository.ProductAvailabilityRepository;
import com.project.lendmate.repository.ProductRepository;
import com.project.lendmate.service.ProductAvailabilityService;
import com.project.lendmate.service.ProductImageService;
import com.project.lendmate.service.StorageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
@Slf4j
public class ProductAvailabilityServiceImpl implements ProductAvailabilityService {
    private final ProductAvailabilityRepository repository;
    private final ProductAvailabilityMapper mapper;

    @Override
    public ProductAvailabilityResponse createProductAvailabilityRecord(ProductAvailabilityRequest request) {
        ProductAvailability model = mapper.toEntity(request);
        ProductAvailability savedRecord = repository.save(model);
        return mapper.toDto(savedRecord);
    }
}
