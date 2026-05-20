package com.project.lendmate.mapper;

import com.project.lendmate.dto.responseDto.ProductImageResponse;
import com.project.lendmate.dto.responseDto.ProductResponse;
import com.project.lendmate.model.Product;
import com.project.lendmate.model.ProductImage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductImageMapper {
    public ProductImageResponse toDto(ProductImage productImage){
        if (productImage == null) return null;
        return ProductImageResponse.builder()
                .id(productImage.getId())
                .productId(productImage.getProductId())
                .isPrimary(productImage.isPrimary())
                .imageUrl(productImage.getImageUrl())
                .build();
    }
}
