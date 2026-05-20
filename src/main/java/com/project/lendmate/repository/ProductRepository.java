package com.project.lendmate.repository;

import com.project.lendmate.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByOwnerIdAndProductName(Long ownerId, String productName);

    //TODO: availability tablosunda enddate'i geçmemiş olanları almamamız lazım
    //TODO: iki tabloda ilgili yerler index eklemek  gerekiyor ki sorgu hızlı çalışsın
    List<Product> findAllByDeletedFalse();

    Optional<Product> findByIdAndDeletedFalse(Long productId);
}
