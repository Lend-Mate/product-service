package com.project.lendmate.repository;

import com.project.lendmate.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByOwnerIdAndProductName(Long ownerId, String productName);
}
