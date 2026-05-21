package com.project.lendmate.repository;

import com.project.lendmate.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByCategoryName(String categoryName);
    boolean existsByCategoryNameAndIdNot(String categoryName, Long id);
    Optional<Category> findByIdAndIsActiveTrue(Long categoryId);
    List<Category> findAllByIsActiveTrue();
}
