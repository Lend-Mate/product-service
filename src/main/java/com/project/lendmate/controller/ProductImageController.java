package com.project.lendmate.controller;

import com.project.lendmate.dto.requestDto.ProductRequest;
import com.project.lendmate.dto.responseDto.ProductImageResponse;
import com.project.lendmate.dto.responseDto.ProductResponse;
import com.project.lendmate.service.ProductImageService;
import com.project.lendmate.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/product-image")
public class ProductImageController {
    private final ProductImageService productImageService;

    @PostMapping
    public ResponseEntity<ProductImageResponse> createProductImage(@RequestBody MultipartFile file){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productImageService.createProductImage(file));
    }
}
