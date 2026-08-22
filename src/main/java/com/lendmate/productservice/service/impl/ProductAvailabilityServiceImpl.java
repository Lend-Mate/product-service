package com.lendmate.productservice.service.impl;

import com.lendmate.productservice.dto.requestDto.ProductAvailabilityRequest;
import com.lendmate.productservice.dto.responseDto.ProductAvailabilityResponse;
import com.lendmate.productservice.expection.ProductAvailabilityNotFound;
import com.lendmate.productservice.mapper.ProductAvailabilityMapper;
import com.lendmate.productservice.model.Enum.Reason;
import com.lendmate.productservice.model.ProductAvailability;
import com.lendmate.productservice.repository.ProductAvailabilityRepository;
import com.lendmate.productservice.repository.ProductRepository;
import com.lendmate.productservice.service.ProductAvailabilityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
@Slf4j
public class ProductAvailabilityServiceImpl implements ProductAvailabilityService {
    private final ProductAvailabilityRepository repository;
    private final ProductAvailabilityMapper mapper;
    private final ProductRepository productRepository;

    @Override
    public List<ProductAvailabilityResponse> createProductAvailabilityRecord(ProductAvailabilityRequest request) {
        List<ProductAvailability> models = IntStream.range(0, request.getQuantity())
                .mapToObj(i -> mapper.toEntity(request))
                .toList();
        List<ProductAvailability> savedRecords = repository.saveAll(models);
        return savedRecords.stream().map(mapper::toDto).toList();
    }

    @Override
    public void deleteProductAvailabilityRecord(Long id) {
        repository.deleteById(id);
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
        List<ProductAvailability> expiredRecords = repository.findByEndDateBefore(date);
        if(expiredRecords.isEmpty()) return 0;

        Map<Long, Long> productIdAndCount = expiredRecords.stream()
                .filter(record -> record.getReason() == Reason.RENTED)
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
