package com.project.lendmate.service.impl;

import com.project.lendmate.dto.request.ProductRequest;
import com.project.lendmate.dto.response.ProductResponse;
import com.project.lendmate.expection.ProductAlreadyExistsException;
import com.project.lendmate.expection.ProductNotFoundException;
import com.project.lendmate.mapper.ProductMapper;
import com.project.lendmate.model.Product;
import com.project.lendmate.repository.ProductRepository;
import com.project.lendmate.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    @Override
    public ProductResponse getProductById(Long productId) {
        log.debug("[getProductById] productId: {}", productId);
        Product product = productRepository.findByIdAndDeletedFalse(productId)
                .orElseThrow(() -> new ProductNotFoundException("Ürün bulunamadı: " + productId));
        ProductResponse response = mapper.toDto(product);
        log.debug("[getProductById] response: {}", response);
        return response;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        log.debug("[getAllProducts]");
       List<Product> products = productRepository.findAllByDeletedFalse();
       List<ProductResponse> response = products.stream()
                       .map(mapper::toDto)
                               .toList();
        log.debug("[getAllProducts] product count: {}", response.size());
        return response;
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        log.debug("[updateProduct] productId: {} request: {}", id, productRequest);
        Product product = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ProductNotFoundException("Ürün bulunamadı: " + id));
        mapper.updateEntity(product, productRequest);
        Product updatedProduct = productRepository.save(product);
        ProductResponse response = mapper.toDto(updatedProduct);
        log.debug("[updateProduct] response: {}", response);
        return response;
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        log.debug("[createProduct] request: {}", productRequest);
        boolean isExists = productRepository.existsByOwnerIdAndProductName(productRequest.getOwnerId(), productRequest.getProductName());
        if (isExists) {
            throw new ProductAlreadyExistsException("Bu ürün zaten mevcut.");
        }
        Product product = mapper.toEntity(productRequest);
        Product savedProduct = productRepository.save(product);
        ProductResponse response = mapper.toDto(savedProduct);
        log.debug("[createProduct] response: {}", response);
        return response;
    }

    @Override
    public void deleteProduct(Long productId) {
        log.debug("[deleteProduct] productId: {}", productId);
        Product product = productRepository.findByIdAndDeletedFalse(productId)
                .orElseThrow(() -> new ProductNotFoundException("Ürün bulunamadı: " + productId));
        product.setDeleted(true);
        product.setDeletedAt(LocalDateTime.now());
        productRepository.save(product);
        log.info("[deleteProduct] deleted productId: {}", productId);
    }
}
