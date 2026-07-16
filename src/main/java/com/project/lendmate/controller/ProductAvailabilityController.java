package com.project.lendmate.controller;

import com.project.lendmate.dto.requestDto.ProductAvailabilityRequest;
import com.project.lendmate.dto.responseDto.ProductAvailabilityResponse;
import com.project.lendmate.service.ProductAvailabilityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/product-availability")
public class ProductAvailabilityController {
    private final ProductAvailabilityService productAvailabilityService;

    @PostMapping
    public ResponseEntity<ProductAvailabilityResponse> createProductAvailabilityRecord(@RequestBody ProductAvailabilityRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productAvailabilityService.createProductAvailabilityRecord(request));
    }

    @GetMapping("/internal/expired-rented")
    public ResponseEntity<Map<String, Integer>> getProductsByExpiredDateAndReason(@RequestParam(required = false) LocalDateTime date) {
        LocalDateTime currentDate = (date != null) ? date : LocalDateTime.now();
        int processedCount = productAvailabilityService.processExpiredRentedProducts(currentDate);
        return ResponseEntity.ok(Map.of("processedCount", processedCount));
    }
}
