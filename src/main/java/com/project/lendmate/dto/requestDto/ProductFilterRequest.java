package com.project.lendmate.dto.requestDto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductFilterRequest {
    private Long categoryId;
    private String brand;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer minRentalDays;
    private Integer maxRentalDays;
}
