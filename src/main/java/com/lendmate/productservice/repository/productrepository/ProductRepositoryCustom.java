package com.lendmate.productservice.repository.productrepository;

import com.lendmate.productservice.model.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ProductRepositoryCustom {
    List<String> findDistinctBrands(Specification<Product> spec);
}