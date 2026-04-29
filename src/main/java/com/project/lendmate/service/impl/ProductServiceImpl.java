package com.project.lendmate.service.impl;

import com.project.lendmate.dto.request.ProductRequest;
import com.project.lendmate.dto.response.ProductResponse;
import com.project.lendmate.mapper.ProductMapper;
import com.project.lendmate.model.Product;
import com.project.lendmate.repository.ProductRepository;
import com.project.lendmate.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    @Override
    public ProductResponse getProductById(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    log.info("Ürün bulundu: {}", product.getProductName());
                    return mapper.toDto(product);
                })
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı: " + id));
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return List.of();
    }

    @Override
    public void updateProduct(ProductRequest productRequest) {

    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = mapper.toEntity(productRequest);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);

        return getProductById(product.getId());
        //TODO: log basılacak

    }

    @Override
    public void deleteProduct(Long productId) {

    }
}
