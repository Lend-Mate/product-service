package com.lendmate.productservice.mapper;

import com.lendmate.productservice.dto.responseDto.ProductImageResponse;
import com.lendmate.productservice.model.ProductImage;
import org.springframework.stereotype.Component;

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
