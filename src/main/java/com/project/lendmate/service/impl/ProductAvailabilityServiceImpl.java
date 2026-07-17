package com.project.lendmate.service.impl;

import com.project.lendmate.dto.requestDto.ProductAvailabilityRequest;
import com.project.lendmate.dto.responseDto.ProductAvailabilityResponse;
import com.project.lendmate.expection.ProductAvailabilityNotFound;
import com.project.lendmate.mapper.ProductAvailabilityMapper;
import com.project.lendmate.model.Enum.Reason;
import com.project.lendmate.model.ProductAvailability;
import com.project.lendmate.repository.ProductAvailabilityRepository;
import com.project.lendmate.repository.ProductRepository;
import com.project.lendmate.service.ProductAvailabilityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ProductAvailabilityServiceImpl implements ProductAvailabilityService {
    private final ProductAvailabilityRepository repository;
    private final ProductAvailabilityMapper mapper;
    private final ProductRepository productRepository;

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

    @Override
    @Transactional
    public int processExpiredRentedProducts(LocalDateTime date) {
        List<ProductAvailability> expiredRecords = repository.findByReasonAndEndDateAfter(Reason.RENTED, date);
        if(expiredRecords.isEmpty()) return 0;

        Map<Long, Long> productIdAndCount = expiredRecords.stream()
                .collect(Collectors.groupingBy(
                        ProductAvailability::getProductId, Collectors.counting()));
        for (Map.Entry<Long, Long> entry: productIdAndCount.entrySet()) {
            productRepository.incrementStock(entry.getKey(), entry.getValue().intValue());
        }
        List<Long> idsToDelete = expiredRecords.stream()
                .map(ProductAvailability::getId)
                .toList();
        repository.deleteAllByIdInBatch(idsToDelete);
        return idsToDelete.size();
    }
}
