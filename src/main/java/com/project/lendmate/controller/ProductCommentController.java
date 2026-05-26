package com.project.lendmate.controller;

import com.project.lendmate.dto.requestDto.ProductCommentRequest;
import com.project.lendmate.dto.responseDto.ProductCommentResponse;
import com.project.lendmate.service.ProductCommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/product-comment")
public class ProductCommentController {

    private final ProductCommentService productCommentService;

    @PostMapping
    public ResponseEntity<ProductCommentResponse> createProductComment(
            @RequestBody ProductCommentRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productCommentService.createProductComment(request));
    }
}