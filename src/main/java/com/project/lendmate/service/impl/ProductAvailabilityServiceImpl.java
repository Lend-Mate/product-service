package com.project.lendmate.service.impl;

import com.project.lendmate.dto.requestDto.ProductAvailabilityRequest;
import com.project.lendmate.dto.responseDto.ProductAvailabilityResponse;
import com.project.lendmate.expection.ProductAvailabilityNotFound;
import com.project.lendmate.mapper.ProductAvailabilityMapper;
import com.project.lendmate.model.ProductAvailability;
import com.project.lendmate.repository.ProductAvailabilityRepository;
import com.project.lendmate.service.ProductAvailabilityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public ProductAvailabilityResponse getProductAvailabilities(long productId) {
        ProductAvailability availability = repository.findById(productId).orElseThrow(()-> new ProductAvailabilityNotFound("Product Availability cannot found"));
        return mapper.toDto(availability);
    }

    @Override
    public List<ProductAvailabilityResponse> getAllProductAvailabilities() {
        List<ProductAvailability> availabilities = repository.findAll();
        return availabilities.stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
