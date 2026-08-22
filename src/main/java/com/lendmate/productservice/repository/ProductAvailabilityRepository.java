package com.lendmate.productservice.repository;

import com.lendmate.productservice.model.ProductAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductAvailabilityRepository extends JpaRepository<ProductAvailability, Long> {

    List<ProductAvailability> findByEndDateBefore(LocalDateTime date);

}
