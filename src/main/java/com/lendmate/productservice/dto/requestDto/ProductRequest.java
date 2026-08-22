package com.lendmate.productservice.dto.requestDto;

import com.lendmate.productservice.model.Enum.Currency;
import com.lendmate.productservice.model.Enum.RentalPeriod;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotNull(message = "Owner id is mandatory")
    private Long ownerId;

    @NotNull(message = "Category id is mandatory")
    private Long categoryId;

    @NotBlank(message = "Product name is mandatory")
    @Size(max = 150, message = "Product name cannot exceed 150 characters")
    private String productName;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotNull(message = "Currency is mandatory")
    private Currency currency;

    @NotNull(message = "Price is mandatory")
    @Digits(integer = 10, fraction = 2)
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    private String brand;

    @NotNull(message = "Stock quantity is mandatory")
    @PositiveOrZero(message = "Stock quantity cannot be negative")
    private Integer stockQuantity;

    @NotEmpty(message = "At least one rental period must be selected.")
    private Set<RentalPeriod> availablePeriods;

    @NotNull(message = "Deposit amount is mandatory")
    @PositiveOrZero(message = "Deposit amount must be positive or zero")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal depositAmount;

    private List<ProductAttributeRequest> attributes;
}