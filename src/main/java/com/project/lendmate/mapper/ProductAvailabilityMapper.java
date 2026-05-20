package com.project.lendmate.mapper;

import com.project.lendmate.dto.requestDto.ProductAvailabilityRequest;
import com.project.lendmate.dto.responseDto.ProductAvailabilityResponse;
import com.project.lendmate.dto.responseDto.ProductImageResponse;
import com.project.lendmate.model.ProductAvailability;
import com.project.lendmate.model.ProductImage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductAvailabilityMapper {
    public ProductAvailability toEntity(ProductAvailabilityRequest request){
        if (request == null) return null;
        return ProductAvailability.builder()
                .productId(request.getProductId())
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
                .productId(model.getProductId())
                .startDate(model.getStartDate())
                .endDate(model.getEndDate())
                .reason(model.getReason())
                .createdAt(model.getCreatedAt())
                .build();
    }
}
