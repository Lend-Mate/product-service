package com.lendmate.productservice.mapper;

import com.lendmate.productservice.dto.requestDto.ProductCommentRequest;
import com.lendmate.productservice.dto.responseDto.ProductCommentResponse;
import com.lendmate.productservice.model.Product;
import com.lendmate.productservice.model.ProductComment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductCommentMapper {

    public ProductComment toEntity(ProductCommentRequest request) {
        if (request == null) return null;

        return ProductComment.builder()
                .product(Product.builder()
                        .id(request.getProductId())
                        .build())
                .userId(request.getUserId())
                .text(request.getText())
                .rating(request.getRating())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public ProductCommentResponse toDto(ProductComment model) {
        if (model == null) return null;

        return ProductCommentResponse.builder()
                .id(model.getId())
                .productId(model.getProduct().getId())
                .userId(model.getUserId())
                .text(model.getText())
                .rating(model.getRating())
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .build();
    }

    public void updateEntity(ProductComment product, ProductCommentRequest request) {
        if (product == null || request == null) return;
        product.setText(request.getText());
        product.setRating(request.getRating());
        product.setUpdatedAt(LocalDateTime.now());
    }
}