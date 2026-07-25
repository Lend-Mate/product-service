package com.project.lendmate.service;

import com.project.lendmate.dto.requestDto.ProductAttributeRequest;
import com.project.lendmate.dto.requestDto.ProductRequest;
import com.project.lendmate.dto.responseDto.ProductAttributeResponse;
import com.project.lendmate.dto.responseDto.ProductResponse;
import com.project.lendmate.expection.ProductAlreadyExistsException;
import com.project.lendmate.expection.ProductNotFoundException;
import com.project.lendmate.mapper.ProductMapper;
import com.project.lendmate.model.Enum.RentalPeriod;
import com.project.lendmate.model.Product;
import com.project.lendmate.repository.ProductRepository;
import com.project.lendmate.service.impl.ProductAttributeServiceImpl;
import com.project.lendmate.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductAttributeServiceImpl productAttributeService;

    private Product product;
    private ProductRequest productRequest;
    private ProductResponse productResponse;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setOwnerId(1L);
        product.setProductName("Kamera");
        product.setPrice(new BigDecimal(1000));

        Set<RentalPeriod> rentalPeriods = new HashSet<>();
        rentalPeriods.add(RentalPeriod.ONE_MONTH);
        rentalPeriods.add(RentalPeriod.SIX_MONTH);
        product.setAvailablePeriods(rentalPeriods);

        productRequest = new ProductRequest();
        productRequest.setOwnerId(1L);
        productRequest.setProductName("Kamera");

        List<ProductAttributeRequest> productAttributeRequestList = new ArrayList<>();
        productAttributeRequestList.add(new ProductAttributeRequest(1L, "", "", LocalDateTime.now()));
        productRequest.setAttributes(productAttributeRequestList);

        productResponse = new ProductResponse();
        productResponse.setId(1L);
        productResponse.setProductName("Kamera");

        List<ProductAttributeResponse> productAttributeResponseList = new ArrayList<>();
        productAttributeResponseList.add(new ProductAttributeResponse(1L, 1L,"", "", LocalDateTime.now()));
        productResponse.setAttributes(productAttributeResponseList);
    }

    // ─── createProduct ───────────────────────────────────────

    @Test
    void createProduct_success() {
        when(productRepository.existsByOwnerIdAndProductName(1L, "Kamera")).thenReturn(false);
        when(mapper.toEntity(productRequest)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(mapper.toDto(product)).thenReturn(productResponse);
        when(productAttributeService.createProductAttributes(product.getId(), productRequest.getAttributes())).thenReturn(productResponse.getAttributes());

        ProductResponse result = productService.createProduct(productRequest);

        assertNotNull(result);
        assertEquals("Kamera", result.getProductName());
        verify(productRepository).save(product);
    }

    @Test
    void createProduct_alreadyExists_throwsException() {
        when(productRepository.existsByOwnerIdAndProductName(1L, "Kamera")).thenReturn(true);

        assertThrows(ProductAlreadyExistsException.class,
                () -> productService.createProduct(productRequest));

        //verify(productRepository, never()).save(any());
    }

    // ─── getProductById ──────────────────────────────────────

    @Test
    void getProductById_success() {
        when(productRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(product));
        when(mapper.toDto(product)).thenReturn(productResponse);

        ProductResponse result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getProductById_notFound_throwsException() {
        when(productRepository.findByIdAndDeletedFalse(99L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> productService.getProductById(99L));
    }

    // ─── getAllProducts ───────────────────────────────────────

//    @Test
//    @Disabled
//    void getAllProducts_success() {
//        List<Product> products = List.of(product);
//        when(productRepository.findAll()).thenReturn(products);
//        when(mapper.toDto(product)).thenReturn(productResponse);
//
//        List<ProductResponse> result = productService.getAllProducts();
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//    }

//    @Test
//    @Disabled
//    void getAllProducts_emptyList() {
//        when(productRepository.findAll()).thenReturn(Collections.emptyList());
//
//        List<ProductResponse> result = productService.getAllProducts();
//
//        assertNotNull(result);
//        assertTrue(result.isEmpty());
//    }

    // ─── updateProduct ───────────────────────────────────────

    @Test
    void updateProduct_success() {
        when(productRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);
        when(mapper.toDto(product)).thenReturn(productResponse);

        ProductResponse result = productService.updateProduct(1L, productRequest);

        assertNotNull(result);
        verify(mapper).updateEntity(product, productRequest);
        verify(productRepository).save(product);
    }

    @Test
    void updateProduct_notFound_throwsException() {
        when(productRepository.findByIdAndDeletedFalse(99L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> productService.updateProduct(99L, productRequest));

        verify(productRepository, never()).save(any());
    }

    // ─── deleteProduct ───────────────────────────────────────

    @Test
    void deleteProduct_success() {
        when(productRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(product));

        productService.deleteProduct(1L);

        verify(productRepository).save(product);
    }
}