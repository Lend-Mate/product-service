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

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    @Override
    public ProductResponse getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Ürün bulunamadı: " + productId));

        log.info("Ürün bulundu: {}", product.getProductName());
        return mapper.toDto(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
       List<Product> listOfProducts = productRepository.findAll();
        return listOfProducts.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Ürün bulunamadı: " + id));
        mapper.updateEntity(product, productRequest);
        return mapper.toDto(productRepository.save(product));
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        boolean isExists = productRepository.existsByOwnerIdAndProductName(productRequest.getOwnerId(), productRequest.getProductName());
        if (isExists) {
            throw new ProductAlreadyExistsException("Bu ürün zaten mevcut.");
        }

        Product product = mapper.toEntity(productRequest);
        productRepository.save(product);

        return getProductById(product.getId());
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Ürün bulunamadı: " + productId));

        productRepository.delete(product);
        log.info("Ürün başarılı bir şekilde silindi: {}",  productId);
    }
}
