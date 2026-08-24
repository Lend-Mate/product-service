package com.lendmate.productservice.mapper;

import com.lendmate.productservice.dto.requestDto.ProductAvailabilityRequest;
import com.lendmate.productservice.dto.responseDto.ProductAvailabilityResponse;
import com.lendmate.productservice.model.Product;
import com.lendmate.productservice.model.ProductAvailability;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductAvailabilityMapper {
    public ProductAvailability toEntity(ProductAvailabilityRequest request){
        if (request == null) return null;
        return ProductAvailability.builder()
                .product(Product.builder()
                        .id(request.getProductId())
                        .build())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .reason(request.getReason())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public ProductAvailabilityResponse toDto(ProductAvailability model){
        if (model == null) return null;
        return ProductAvailabilityResponse.builder()
                .id(model.getId())
                .productId(model.getProduct().getId())
                .startDate(model.getStartDate())
                .endDate(model.getEndDate())
                .reason(model.getReason())
                .createdAt(model.getCreatedAt())
                .build();
    }
}
