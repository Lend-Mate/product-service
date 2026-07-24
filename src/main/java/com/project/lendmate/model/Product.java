package com.project.lendmate.model;

import com.project.lendmate.model.Enum.Currency;
import com.project.lendmate.model.Enum.RentalPeriod;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "product_rental_periods",
            joinColumns = @JoinColumn(name = "product_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "period", nullable = false)
    private Set<RentalPeriod> availablePeriods;

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

    @OneToMany(mappedBy = "productId", cascade = CascadeType.ALL)
    private List<ProductAttribute> attributes;
}
