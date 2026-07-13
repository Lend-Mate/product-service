package com.project.lendmate.repository;

import com.project.lendmate.model.Product;
import com.project.lendmate.model.projection.ProductQuantityProjection;
import com.project.lendmate.repository.productrepository.ProductRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>, ProductRepositoryCustom {
    boolean existsByOwnerIdAndProductName(Long ownerId, String productName);

    Optional<Product> findByIdAndDeletedFalse(Long productId);

    void deleteAllByOwnerId(Long id);

    List<ProductQuantityProjection> findByIdIn(List<Long> ids);
}