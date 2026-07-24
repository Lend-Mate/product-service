package com.project.lendmate.dto.responseDto;

import com.project.lendmate.model.Enum.Currency;
import com.project.lendmate.model.Enum.RentalPeriod;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Long id;
    private Long ownerId;
    private Long categoryId;
    private String productName;
    private String description;
    private Currency currency;
    private BigDecimal price;
    private String brand;
    private Integer stockQuantity;
    private BigDecimal depositAmount;
    private List<ProductImageResponse> images;
    private List<ProductCommentResponse> comments;
    private List<ProductAvailabilityResponse> availabilities;
    private Map<RentalPeriod, BigDecimal> rentalPeriodPrices;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private List<ProductAttributeResponse> attributes;
}