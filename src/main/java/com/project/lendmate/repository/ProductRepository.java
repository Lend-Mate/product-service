package com.project.lendmate.repository;

import com.project.lendmate.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    boolean existsByOwnerIdAndProductName(Long ownerId, String productName);

    Optional<Product> findByIdAndDeletedFalse(Long productId);

    void deleteAllByOwnerId(Long id);

    @Query("""
    SELECT DISTINCT p.brand
    FROM Product p
    WHERE p.deleted = false
      AND p.brand IS NOT NULL
    ORDER BY p.brand
    """)
    List<String> findDistinctBrands();
}
