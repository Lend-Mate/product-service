package com.project.lendmate.mapper;

import com.project.lendmate.dto.responseDto.*;
import com.project.lendmate.model.Enum.RentalPeriod;
import com.project.lendmate.model.Product;
import com.project.lendmate.dto.requestDto.ProductRequest;
import com.project.lendmate.model.ProductAvailability;
import com.project.lendmate.model.ProductComment;
import com.project.lendmate.model.ProductImage;
import com.project.lendmate.util.RentalPriceCalculator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ProductMapper {
    private final ProductImageMapper productImageMapper;
    private final ProductCommentMapper productCommentMapper;
    private final ProductAvailabilityMapper productAvailabilityMapper;

    public Product toEntity(ProductRequest productRequest){
        if(productRequest == null) return null;
        return Product.builder()
                .ownerId(productRequest.getOwnerId())
                .categoryId(productRequest.getCategoryId())
                .productName(productRequest.getProductName())
                .description(productRequest.getDescription())
                .currency(productRequest.getCurrency())
                .price(productRequest.getPrice())
                .brand(productRequest.getBrand())
                .stockQuantity(productRequest.getStockQuantity())
                .availablePeriods(productRequest.getAvailablePeriods())
                .depositAmount(productRequest.getDepositAmount())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public ProductResponse toDto(Product product){
        if (product == null) return null;

        return ProductResponse.builder()
                .id(product.getId())
                .ownerId(product.getOwnerId())
                .categoryId(product.getCategoryId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .currency(product.getCurrency())
                .price(product.getPrice())
                .brand(product.getBrand())
                .stockQuantity(product.getStockQuantity())
                .depositAmount(product.getDepositAmount())
                .images(mapImages(product.getImages()))
                .comments(mapComments(product.getComments()))
                .availabilities(mapAvailabilities(product.getAvailabilities()))
                .rentalPeriodPrices(getPeriodPrices(product))
                .createdAt(product.getCreatedAt())
                .updateAt(product.getUpdatedAt())
                .build();
    }

    public void updateEntity(Product product, ProductRequest request) {
        if (request.getOwnerId() != null)       product.setOwnerId(request.getOwnerId());
        if (request.getCategoryId() != null)    product.setCategoryId(request.getCategoryId());
        if (request.getProductName() != null)   product.setProductName(request.getProductName());
        if (request.getDescription() != null)   product.setDescription(request.getDescription());
        if (request.getCurrency() != null)      product.setCurrency(request.getCurrency());
        if (request.getPrice() != null)         product.setPrice(request.getPrice());
        if (request.getBrand() != null)         product.setBrand(request.getBrand());
        if (request.getStockQuantity() != null) product.setStockQuantity(request.getStockQuantity());
        if (request.getAvailablePeriods() != null) product.setAvailablePeriods(request.getAvailablePeriods());
        if (request.getDepositAmount() != null) product.setDepositAmount(request.getDepositAmount());
        product.setUpdatedAt(LocalDateTime.now());
    }

    private List<ProductImageResponse> mapImages(List<ProductImage> images) {
        if (images == null) return List.of();
        return images.stream().map(productImageMapper::toDto).toList();
    }

    private List<ProductCommentResponse> mapComments(List<ProductComment> comments) {
        if (comments == null) return List.of();
        return comments.stream().map(productCommentMapper::toDto).toList();
    }

    private List<ProductAvailabilityResponse> mapAvailabilities(List<ProductAvailability> availabilities) {
        if (availabilities == null) return List.of();
        return availabilities.stream().map(productAvailabilityMapper::toDto).toList();
    }

    private Map<RentalPeriod, BigDecimal> getPeriodPrices(Product product) {
        return product.getAvailablePeriods().stream()
                .collect(Collectors.toMap(
                        period -> period,
                        period -> RentalPriceCalculator.calculateTotalPrice(product.getPrice(), period)));

    }
}
