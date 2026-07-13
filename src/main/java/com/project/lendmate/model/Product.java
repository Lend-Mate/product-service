package com.project.lendmate.model;

import com.project.lendmate.model.Enum.Currency;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ownerId;

    private Long categoryId;

    private String productName;

    private String description;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private BigDecimal price;

    private String brand;

    private Integer stockQuantity;

    private int minRentalDays;

    private int maxRentalDays;

    private BigDecimal depositAmount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private boolean deleted = false;

    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "productId", cascade = CascadeType.ALL)
    private List<ProductImage> images;

    @OneToMany(mappedBy = "productId", cascade = CascadeType.ALL)
    private List<ProductComment> comments;

    @OneToMany(mappedBy = "productId", cascade = CascadeType.ALL)
    private List<ProductAvailability> availabilities;
}
