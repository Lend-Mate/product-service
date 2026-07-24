package com.project.lendmate.repository;

import com.project.lendmate.model.ProductAttribute;
import com.project.lendmate.model.ProductAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {

}
