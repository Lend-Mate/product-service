package com.lendmate.productservice.controller;

import com.lendmate.productservice.dto.responseDto.ProductImageResponse;
import com.lendmate.productservice.service.ProductImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/product-image")
public class ProductImageController {
    private final ProductImageService productImageService;

    @PostMapping("/{productId}/images")
    public ResponseEntity<List<ProductImageResponse>>createProductImage(@PathVariable Long productId,
                                                                        @RequestBody List<String> imageNames){
        return ResponseEntity.ok(productImageService.createProductImage(productId, imageNames));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProductImages(@RequestBody List<Long> imageIds){
        productImageService.deleteProductImages(imageIds);
        return ResponseEntity.noContent().build();
    }
}
