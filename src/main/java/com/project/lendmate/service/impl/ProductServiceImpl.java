package com.project.lendmate.service.impl;

import com.project.lendmate.document.ProductDocument;
import com.project.lendmate.dto.requestDto.ProductFilterRequest;
import com.project.lendmate.dto.requestDto.ProductRequest;
import com.project.lendmate.dto.requestDto.ProductSearchFilterRequest;
import com.project.lendmate.dto.responseDto.ProductResponse;
import com.project.lendmate.expection.ProductAlreadyExistsException;
import com.project.lendmate.expection.ProductNotFoundException;
import com.project.lendmate.mapper.ProductMapper;
import com.project.lendmate.model.Product;
import com.project.lendmate.repository.ProductRepository;
import com.project.lendmate.repository.ProductSearchRepository;
import com.project.lendmate.repository.specification.ProductElasticsearchQueryBuilder;
import com.project.lendmate.repository.specification.ProductSpecification;
import com.project.lendmate.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductSearchRepository productSearchRepository;
    private final ProductElasticsearchQueryBuilder productElasticsearchQueryBuilder;
    private final ProductMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long productId) {
        Product product = productRepository.findByIdAndDeletedFalse(productId)
                .orElseThrow(() -> new ProductNotFoundException("Ürün bulunamadı: " + productId));
        return mapper.toDto(product);
    }

    @Override
    public Page<ProductResponse> getAllProducts(int page, int size, String sortBy, boolean ascending, ProductFilterRequest filter) {
       Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
       Pageable pageable = PageRequest.of(page, size, sort);

       Specification<Product> spec = ProductSpecification.withFilters(filter);
       Page<Product> products = productRepository.findAll(spec, pageable);
       return products.map(mapper::toDto);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ProductNotFoundException("Ürün bulunamadı: " + id));
        mapper.updateEntity(product, productRequest);
        Product updatedProduct = productRepository.save(product);
        return mapper.toDto(updatedProduct);
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        boolean isExists = productRepository.existsByOwnerIdAndProductName(productRequest.getOwnerId(), productRequest.getProductName());
        if (isExists) {
            throw new ProductAlreadyExistsException("Bu ürün zaten mevcut.");
        }
        Product product = mapper.toEntity(productRequest);
        Product savedProduct = productRepository.save(product);
        return mapper.toDto(savedProduct);
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.findByIdAndDeletedFalse(productId)
                .orElseThrow(() -> new ProductNotFoundException("Ürün bulunamadı: " + productId));
        product.setDeleted(true);
        product.setDeletedAt(LocalDateTime.now());
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void deleteProductsByOwner(Long ownerId) {
        productRepository.deleteAllByOwnerId(ownerId);
    }

    @Override
    public List<ProductResponse> getProductsByIds(List<Long> ids) {
        return productRepository.findAllById(ids).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public Page<Long> searchProductsByIds(ProductSearchFilterRequest filter, String query, int page, int size, String sortBy, boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

//        Page<ProductDocument> results = productSearchRepository
//                .findByProductNameContainingOrDescriptionContaining(query, query, pageable);

        Page<ProductDocument> results = productElasticsearchQueryBuilder.searchWithFilters(filter, pageable);

        return results.map(doc -> Long.valueOf(doc.getId()));
    }

    @Override
    public List<String> getUniqueBrands() {
        return productRepository.findDistinctBrands();
    }
}
