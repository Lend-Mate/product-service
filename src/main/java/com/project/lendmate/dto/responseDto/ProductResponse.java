package com.project.lendmate.dto.response;

import com.project.lendmate.model.Enum.Currency;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private Integer minRentalDays;
    private Integer maxRentalDays;
    private BigDecimal depositAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}