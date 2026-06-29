package com.project.lendmate.mapper;

import com.project.lendmate.dto.requestDto.ProductCommentRequest;
import com.project.lendmate.dto.responseDto.ProductCommentResponse;
import com.project.lendmate.model.ProductComment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductCommentMapper {

    public ProductComment toEntity(ProductCommentRequest request) {
        if (request == null) return null;

        return ProductComment.builder()
                .productId(request.getProductId())
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
                .productId(model.getProductId())
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