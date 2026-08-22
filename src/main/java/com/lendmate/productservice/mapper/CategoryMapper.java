package com.lendmate.productservice.mapper;

import com.lendmate.productservice.dto.requestDto.CategoryRequest;
import com.lendmate.productservice.dto.responseDto.CategoryResponse;
import com.lendmate.productservice.model.Category;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequest request) {
        if (request == null) return null;
        return Category.builder()
                .categoryName(request.getCategoryName())
                .description(request.getDescription())
                .imageName(request.getImageName())
                .isActive(Boolean.TRUE.equals(request.getIsActive()))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public CategoryResponse toDto(Category category) {
        if (category == null) return null;
        return CategoryResponse.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .description(category.getDescription())
                .imageName(category.getImageName())
                .isActive(category.isActive())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    public void updateEntity(Category category, CategoryRequest request) {
        if (request.getCategoryName() != null) {
            category.setCategoryName(request.getCategoryName());
        }
        if (request.getDescription() != null) {
            category.setDescription(request.getDescription());
        }
        if (request.getIsActive() != null) {
            category.setActive(request.getIsActive());
        }
        category.setUpdatedAt(LocalDateTime.now());
    }
}
