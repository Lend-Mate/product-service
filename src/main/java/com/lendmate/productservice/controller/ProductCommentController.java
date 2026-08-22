package com.lendmate.productservice.controller;

import com.lendmate.productservice.dto.requestDto.ProductCommentRequest;
import com.lendmate.productservice.dto.responseDto.ProductCommentResponse;
import com.lendmate.productservice.service.ProductCommentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/product-comments")
public class ProductCommentController {

    private final ProductCommentService productCommentService;

    @PostMapping
    public ResponseEntity<ProductCommentResponse> createProductComment(
           @Valid @RequestBody ProductCommentRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productCommentService.createProductComment(request));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<ProductCommentResponse>> getComments(@PathVariable Long id){
        return ResponseEntity.ok()
                .body(productCommentService.getComments(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductCommentResponse> updateProductComment(@PathVariable Long id, @Valid @RequestBody ProductCommentRequest request){
        return ResponseEntity.ok()
                .body(productCommentService.updateProductComment(id, request));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProductComments(@RequestBody List<Long> commentIds){
        productCommentService.deleteProductComments(commentIds);
        return ResponseEntity.noContent().build();
    }
}