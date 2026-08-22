package com.lendmate.productservice.service.impl;

//import com.project.lendmate.document.ProductDocument;
import com.lendmate.productservice.dto.requestDto.ProductFilterRequest;
import com.lendmate.productservice.dto.requestDto.ProductRequest;
import com.lendmate.productservice.dto.requestDto.ProductSearchFilterRequest;
import com.lendmate.productservice.dto.responseDto.ProductResponse;
import com.lendmate.productservice.expection.ProductAlreadyExistsException;
import com.lendmate.productservice.expection.ProductNotFoundException;
import com.lendmate.productservice.mapper.ProductMapper;
import com.lendmate.productservice.model.Product;
import com.lendmate.productservice.model.projection.ProductQuantityProjection;
import com.lendmate.productservice.repository.ProductRepository;
//import com.project.lendmate.repository.ProductSearchRepository;
//import com.project.lendmate.repository.specification.ProductElasticsearchQueryBuilder;
import com.lendmate.productservice.repository.specification.ProductSpecification;
import com.lendmate.productservice.service.ProductAttributeService;
import com.lendmate.productservice.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductAttributeService productAttributeService;
    //private final ProductElasticsearchQueryBuilder productElasticsearchQueryBuilder;
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
    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ProductNotFoundException("Ürün bulunamadı: " + id));
        mapper.updateEntity(product, productRequest);
        Product updatedProduct = productRepository.save(product);
        return mapper.toDto(updatedProduct);
    }

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest productRequest) {
        boolean isExists = productRepository.existsByOwnerIdAndProductName(productRequest.getOwnerId(), productRequest.getProductName());
        if (isExists) {
            throw new ProductAlreadyExistsException("Bu ürün zaten mevcut.");
        }
        Product product = mapper.toEntity(productRequest);
        Product savedProduct = productRepository.save(product);

        productAttributeService.createProductAttributes(savedProduct.getId(), productRequest.getAttributes());

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

        //Page<ProductDocument> results = productElasticsearchQueryBuilder.searchWithFilters(filter, pageable);
        //return results.map(doc -> Long.valueOf(doc.getId()));
        return null;
    }

    @Override
    public List<String> getUniqueBrands(ProductFilterRequest filter) {
        Specification<Product> spec = ProductSpecification.withFilters(filter);
        return productRepository.findDistinctBrands(spec);
    }

    @Override
    public Map<Long, Integer> getProductQuantities(List<Long> ids) {
        List<ProductQuantityProjection> quantities = productRepository.findByIdIn(ids);
        return quantities.stream()
                .collect(Collectors.toMap(ProductQuantityProjection::getId, ProductQuantityProjection::getStockQuantity));
    }
}
