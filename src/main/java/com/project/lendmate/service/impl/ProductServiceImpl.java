package com.project.lendmate.service.impl;

import com.project.lendmate.dto.requestDto.ProductRequest;
import com.project.lendmate.dto.responseDto.ProductResponse;
import com.project.lendmate.expection.ProductAlreadyExistsException;
import com.project.lendmate.expection.ProductNotFoundException;
import com.project.lendmate.mapper.ProductMapper;
import com.project.lendmate.model.Product;
import com.project.lendmate.repository.ProductRepository;
import com.project.lendmate.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
    private final ProductMapper mapper;

    @Override
    //@Cacheable(value = "products", key = "#productId")
    public ProductResponse getProductById(Long productId) {
        Product product = productRepository.findByIdAndDeletedFalse(productId)
                .orElseThrow(() -> new ProductNotFoundException("Ürün bulunamadı: " + productId));
        return mapper.toDto(product);
    }

    @Override
    @Cacheable(value = "productLists", key = "'all'")
    public List<ProductResponse> getAllProducts() {
       List<Product> products = productRepository.findAll();
        return products.stream()
                        .map(mapper::toDto)
                        .collect(Collectors.toList());
    }

    @Override
    @Caching(evict = {
            //@CacheEvict(value = "products", key = "#id"),
            @CacheEvict(value = "productLists", key = "'all'")
    })
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ProductNotFoundException("Ürün bulunamadı: " + id));
        mapper.updateEntity(product, productRequest);
        Product updatedProduct = productRepository.save(product);
        return mapper.toDto(updatedProduct);
    }

    @Override
    @CacheEvict(value = "productLists", key = "'all'")
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
    @CacheEvict(value = "productLists", key = "'all'")
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
}
