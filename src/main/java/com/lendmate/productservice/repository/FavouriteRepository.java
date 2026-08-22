package com.lendmate.productservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lendmate.productservice.model.Favourite;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    boolean existsByProductIdAndUserId(Long productId, Long userId);
    Optional<Favourite> findByProductIdAndUserId(Long productId, Long userId);
    List<Favourite> findAllByUserId(Long userId);
}
