package com.project.lendmate.service.impl;

import com.project.lendmate.dto.requestDto.CategoryRequest;
import com.project.lendmate.dto.responseDto.CategoryResponse;
import com.project.lendmate.expection.CategoryAlreadyExistsException;
import com.project.lendmate.expection.CategoryNotFoundException;
import com.project.lendmate.mapper.CategoryMapper;
import com.project.lendmate.model.Category;
import com.project.lendmate.repository.CategoryRepository;
import com.project.lendmate.service.CategoryService;
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
