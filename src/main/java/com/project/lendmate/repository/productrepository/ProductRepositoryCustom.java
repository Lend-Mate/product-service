package com.project.lendmate.repository.productrepository;

import com.project.lendmate.model.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ProductRepositoryCustom {
    List<String> findDistinctBrands(Specification<Product> spec);
}