package com.project.lendmate.repository;

import com.project.lendmate.document.ProductDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSearchRepository extends ElasticsearchRepository<ProductDocument, String> {

    List<ProductDocument> findByProductNameContainingOrDescriptionContaining(
            String productName, String description, Pageable pageable);
}