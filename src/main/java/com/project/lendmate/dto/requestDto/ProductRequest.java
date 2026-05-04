package com.project.lendmate.dto.request;

import com.project.lendmate.model.Enum.Currency;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Min(value = 1, message = "Minimum rental day must be at least 1")
    private Integer minRentalDays;

    @Min(value = 1, message = "Maximum rental day must be at least 1")
    private Integer maxRentalDays;

    @NotNull(message = "Deposit amount is mandatory")
    @PositiveOrZero(message = "Deposit amount must be positive or zero")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal depositAmount;
}