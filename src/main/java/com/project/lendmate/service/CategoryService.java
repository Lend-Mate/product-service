package com.project.lendmate.service;

import com.project.lendmate.dto.requestDto.CategoryRequest;
import com.project.lendmate.dto.responseDto.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse getCategoryById(Long id);
    List<CategoryResponse> getAllCategories();
    CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest);
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    void deleteCategory(Long categoryId);
}
