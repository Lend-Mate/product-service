package com.project.lendmate.controller;

import com.project.lendmate.dto.requestDto.ProductAvailabilityRequest;
import com.project.lendmate.dto.responseDto.ProductAvailabilityResponse;
import com.project.lendmate.dto.responseDto.ProductImageResponse;
import com.project.lendmate.service.ProductAvailabilityService;
import com.project.lendmate.service.ProductImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/product-availability")
public class ProductAvailabilityController {
    private final ProductAvailabilityService productAvailabilityService;

    @PostMapping
    public ResponseEntity<ProductAvailabilityResponse> createProductAvailabilityRecord(@RequestBody ProductAvailabilityRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productAvailabilityService.createProductAvailabilityRecord(request));
    }
}
