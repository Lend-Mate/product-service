package com.project.lendmate.dto.request;

import com.project.lendmate.model.Enum.Currency;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

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
}