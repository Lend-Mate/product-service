package com.project.lendmate.controller;

import com.project.lendmate.dto.requestDto.ProductFilterRequest;
import com.project.lendmate.dto.requestDto.ProductSearchFilterRequest;
import com.project.lendmate.model.Enum.Reason;
import com.project.lendmate.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.lendmate.dto.requestDto.ProductRequest;
import com.project.lendmate.dto.responseDto.ProductResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/products")
@SecurityRequirement(name = "Bearer Authentication")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/health")
    public String healthCheck() {
        return "product service is up and working...";
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.createProduct(request));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {

        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending,

            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) List<String> brands,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Integer minRentalDays,
            @RequestParam(required = false) Integer maxRentalDays
    ) {
        ProductFilterRequest filter = ProductFilterRequest.builder()
                .categoryId(categoryId)
                .brands(brands)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .minRentalDays(minRentalDays)
                .maxRentalDays(maxRentalDays)
                .build();

        return ResponseEntity.ok(productService.getAllProducts(page, size, sortBy, ascending, filter));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest productRequest) {

        return ResponseEntity.ok(productService.updateProduct(id, productRequest));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/batch")
    public ResponseEntity<List<ProductResponse>> getProductsByIds(
            @RequestParam List<Long> ids) {
        return ResponseEntity.ok(productService.getProductsByIds(ids));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponse>> searchProducts(
            @RequestParam String text,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending,

            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) List<String> brands,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Integer minRentalDays,
            @RequestParam(required = false) Integer maxRentalDays) {

        ProductSearchFilterRequest filter = ProductSearchFilterRequest.builder()
                .query(text)
                .categoryId(categoryId)
                .brands(brands)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .minRentalDays(minRentalDays)
                .maxRentalDays(maxRentalDays)
                .build();

        Page<Long> productIds = productService.searchProductsByIds(filter, text, page, size, sortBy, ascending);

        List<ProductResponse> products = productService.getProductsByIds(productIds.getContent());

        products = products.stream()
                .filter(product -> product.getAvailabilities().stream()
                        .noneMatch(availability -> availability.getReason().equals(Reason.BLOCKED) || availability.getReason().equals(Reason.MAINTENANCE)))
                .toList();

        Page<ProductResponse> result = new PageImpl<>(
                products,
                PageRequest.of(page, size),
                productIds.getTotalElements()
        );

        return ResponseEntity.ok(result);
    }

    @GetMapping("/brands")
    public ResponseEntity<List<String>> getUniqueBrands(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Integer minRentalDays,
            @RequestParam(required = false) Integer maxRentalDays
    ) {
        ProductFilterRequest filter = ProductFilterRequest.builder()
                .query(text)
                .categoryId(categoryId)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .minRentalDays(minRentalDays)
                .maxRentalDays(maxRentalDays)
                .build();

        return ResponseEntity.ok(productService.getUniqueBrands(filter));
    }

    @PostMapping("/internal/quantities")
    public ResponseEntity<Map<Long, Integer>> getProductQuantities(@RequestBody List<Long> ids) {
        return ResponseEntity.ok(productService.getProductQuantities(ids));
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<List<ProductResponse>> getAllProductsByOwnerId(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getAllProductsByOwnerId(id));
    }
}
