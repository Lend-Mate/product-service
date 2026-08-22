package com.lendmate.productservice.service.impl;

import com.lendmate.productservice.dto.requestDto.CategoryRequest;
import com.lendmate.productservice.dto.responseDto.CategoryResponse;
import com.lendmate.productservice.expection.CategoryAlreadyExistsException;
import com.lendmate.productservice.expection.CategoryNotFoundException;
import com.lendmate.productservice.mapper.CategoryMapper;
import com.lendmate.productservice.model.Category;
import com.lendmate.productservice.repository.CategoryRepository;
import com.lendmate.productservice.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new CategoryNotFoundException("Kategori bulunamadı: " + id));
        return mapper.toDto(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAllByIsActiveTrue().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new CategoryNotFoundException("Kategori bulunamadı: " + id));

        if (categoryRequest.getCategoryName() != null
                && categoryRepository.existsByCategoryNameAndIdNot(categoryRequest.getCategoryName(), id)) {
            throw new CategoryAlreadyExistsException("Bu kategori adı zaten mevcut.");
        }

        mapper.updateEntity(category, categoryRequest);
        Category updatedCategory = categoryRepository.save(category);
        return mapper.toDto(updatedCategory);
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        if (categoryRepository.existsByCategoryName(categoryRequest.getCategoryName())) {
            throw new CategoryAlreadyExistsException("Bu kategori zaten mevcut.");
        }

        Category category = mapper.toEntity(categoryRequest);
        Category savedCategory = categoryRepository.save(category);
        return mapper.toDto(savedCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findByIdAndIsActiveTrue(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Kategori bulunamadı: " + categoryId));
        category.setActive(false);
        category.setUpdatedAt(LocalDateTime.now());
        categoryRepository.save(category);
    }
}
