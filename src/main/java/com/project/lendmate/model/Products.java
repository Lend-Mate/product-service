package com.project.lendmate.model;

import com.project.lendmate.model.Enum.Currency;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int ownerId;

    private int categoryId;

    private String productName;

    private String description;

    @Enumerated(EnumType.STRING)
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
