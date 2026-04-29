package com.project.lendmate.mapper;

import com.project.lendmate.model.Product;
import com.project.lendmate.dto.request.ProductRequest;
import com.project.lendmate.dto.response.ProductResponse;
import org.springframework.stereotype.Component;

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
                .minRentalDays(productRequest.getMaxRentalDays())
                .depositAmount(productRequest.getDepositAmount())
                .build();
    }

    public com.project.lendmate.dto.response.ProductResponse toDto(Product product){
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
                .minRentalDays(product.getMaxRentalDays())
                .depositAmount(product.getDepositAmount())
                .createdAt(product.getCreatedAt())
                .updateAt(product.getUpdatedAt())
                .build();
    }
}
