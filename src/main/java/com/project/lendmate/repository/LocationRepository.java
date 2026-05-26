package com.project.lendmate.repository;

import com.project.lendmate.model.Location;
import com.project.lendmate.model.ProductAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
