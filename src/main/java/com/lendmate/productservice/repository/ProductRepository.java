package com.lendmate.productservice.repository;

import com.lendmate.productservice.model.Product;
import com.lendmate.productservice.model.projection.ProductQuantityProjection;
import com.lendmate.productservice.repository.productrepository.ProductRepositoryCustom;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>, ProductRepositoryCustom {
    boolean existsByOwnerIdAndProductName(Long ownerId, String productName);

    Optional<Product> findByIdAndDeletedFalse(Long productId);

    void deleteAllByOwnerId(Long id);

    List<ProductQuantityProjection> findByIdIn(List<Long> ids);

    @Modifying
    @Query("update Product p set p.stockQuantity = p.stockQuantity + :amount where p.id = :productId")
    void incrementStock(@Param("productId") Long productId, @Param("amount")  Integer amount);
}