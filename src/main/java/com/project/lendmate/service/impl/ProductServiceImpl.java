package com.project.lendmate.service.impl;

import com.project.lendmate.document.ProductDocument;
import com.project.lendmate.dto.requestDto.ProductRequest;
import com.project.lendmate.dto.responseDto.ProductResponse;
import com.project.lendmate.expection.ProductAlreadyExistsException;
import com.project.lendmate.expection.ProductNotFoundException;
import com.project.lendmate.mapper.ProductMapper;
import com.project.lendmate.model.Product;
import com.project.lendmate.repository.ProductRepository;
import com.project.lendmate.repository.ProductSearchRepository;
import com.project.lendmate.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductSearchRepository productSearchRepository;
    private final ProductMapper mapper;

    @Override
    public ProductResponse getProductById(Long productId) {
        Product product = productRepository.findByIdAndDeletedFalse(productId)
                .orElseThrow(() -> new ProductNotFoundException("Ürün bulunamadı: " + productId));
        return mapper.toDto(product);
    }

    @Override
    public Page<ProductResponse> getAllProducts(int page, int size, String sortBy, boolean ascending) {
       Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
       Pageable pageable = PageRequest.of(page, size, sort);

       Page<Product> products = productRepository.findAvailableProducts(pageable);
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
    public Page<ProductResponse> getProductsByIds(List<Long> ids, int page, int size, String sortBy, boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository.findAllById(ids, pageable).map(mapper::toDto);
    }

    @Override
    public List<ProductResponse> getProductsByIds(List<Long> ids) {
        return productRepository.findAllById(ids).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<Long> searchProductsByIds(String query) {
        List<ProductDocument> results = productSearchRepository
                .findByProductNameContainingOrDescriptionContaining(query, query);

        return results.stream()
                .map(doc -> Long.valueOf(doc.getId()))
                .toList();
    }

    @Override
    public Page<ProductResponse> searchProductsPostgres(String query, int page, int size, String sortBy, boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> products = productRepository.searchByNameOrDescription(query, pageable);
        return products.map(mapper::toDto);
    }
}
