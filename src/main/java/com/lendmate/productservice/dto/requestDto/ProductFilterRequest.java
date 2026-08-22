package com.lendmate.productservice.dto.requestDto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ProductFilterRequest {
    private String query;
    private Long ownerId;
    private Long categoryId;
    private List<String> brands;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer minRentalDays;
    private Integer maxRentalDays;
}
