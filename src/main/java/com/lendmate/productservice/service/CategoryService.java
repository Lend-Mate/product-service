package com.lendmate.productservice.service;

import com.lendmate.productservice.dto.requestDto.CategoryRequest;
import com.lendmate.productservice.dto.responseDto.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse getCategoryById(Long id);
    List<CategoryResponse> getAllCategories();
    CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest);
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    void deleteCategory(Long categoryId);
}
