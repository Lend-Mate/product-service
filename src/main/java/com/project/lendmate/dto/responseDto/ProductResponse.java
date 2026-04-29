package com.project.lendmate.dto.response;

import com.project.lendmate.model.Enum.Currency;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private Long id;
    private Long ownerId;
    private Long categoryId;
    private String productName;
    private String description;
    private Currency currency;
    private BigDecimal price;
    private String brand;
    private int stockQuantity;
    private int minRentalDays;
    private int maxRentalDays;
    private BigDecimal depositAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}