package com.project.lendmate.repository;

import com.project.lendmate.model.Product;
import com.project.lendmate.model.ProductAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAvailabilityRepository extends JpaRepository<ProductAvailability, Long> {
}
