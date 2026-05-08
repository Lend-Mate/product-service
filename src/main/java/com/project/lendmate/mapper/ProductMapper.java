package com.project.lendmate.mapper;

import com.project.lendmate.model.Product;
import com.project.lendmate.dto.requestDto.ProductRequest;
import com.project.lendmate.dto.responseDto.ProductResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductMapper {
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
                .minRentalDays(productRequest.getMinRentalDays())
                .maxRentalDays(productRequest.getMaxRentalDays())
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
                .minRentalDays(product.getMinRentalDays())
                .maxRentalDays(product.getMaxRentalDays())
                .depositAmount(product.getDepositAmount())
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
        if (request.getMinRentalDays() != null) product.setMinRentalDays(request.getMinRentalDays());
        if (request.getMaxRentalDays() != null) product.setMaxRentalDays(request.getMaxRentalDays());
        if (request.getDepositAmount() != null) product.setDepositAmount(request.getDepositAmount());
        product.setUpdatedAt(LocalDateTime.now());
    }
}
