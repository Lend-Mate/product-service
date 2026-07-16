package com.project.lendmate.repository;

import com.project.lendmate.model.Enum.Reason;
import com.project.lendmate.model.ProductAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductAvailabilityRepository extends JpaRepository<ProductAvailability, Long> {

    List<ProductAvailability> findByReasonAndEndDateBefore(Reason reason, LocalDateTime date);

}
