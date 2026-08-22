//package com.lendmate.productservice.repository;
//
//import com.lendmate.productservice.document.ProductDocument;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface ProductSearchRepository extends ElasticsearchRepository<ProductDocument, String> {
//
//    Page<ProductDocument> findByProductNameContainingOrDescriptionContaining(
//            String productName, String description, Pageable pageable);
//}