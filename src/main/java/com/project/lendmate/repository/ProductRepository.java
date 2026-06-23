package com.project.lendmate.repository;

import com.project.lendmate.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByOwnerIdAndProductName(Long ownerId, String productName);

    @Query(value = """
    SELECT p.*
    FROM product p
    LEFT JOIN product_availability pa ON p.id = pa.product_id
    WHERE p.deleted = false
    AND (
        pa.product_id IS NULL
        OR now() NOT BETWEEN pa.start_date AND pa.end_date
    )
    ORDER BY id
    """, countQuery = """
        SELECT COUNT(DISTINCT p.id)
        FROM product p
        LEFT JOIN product_availability pa ON p.id = pa.product_id
        WHERE p.deleted = false
        AND (
            pa.product_id IS NULL
            OR now() NOT BETWEEN pa.start_date AND pa.end_date
        )
    """, nativeQuery = true)
    Page<Product> findAvailableProducts(Pageable pageable);

    Optional<Product> findByIdAndDeletedFalse(Long productId);

    void deleteAllByOwnerId(Long id);

    @Query(value = """
    SELECT * FROM product
    WHERE deleted = false
    AND (
        product_name ILIKE '%' || :query || '%'
        OR description ILIKE '%' || :query || '%'
    )
    ORDER BY id
    """, countQuery = """
    SELECT COUNT(*)
    FROM product
    WHERE deleted = false
    AND (
        product_name ILIKE '%' || :query || '%'
        OR description ILIKE '%' || :query || '%'
    )
    """, nativeQuery = true)
    Page<Product> searchByNameOrDescription(@Param("query") String query, Pageable pageable);

    @Query(value = """
    SELECT *
    FROM product
    WHERE id IN :ids
    ORDER BY id
    """, countQuery = """
    SELECT COUNT(*)
    FROM product
    WHERE id IN :ids
    """, nativeQuery = true)
    Page<Product> findAllById(List<Long> ids, Pageable pageable);
}
