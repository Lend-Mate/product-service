package com.project.lendmate.controller;

import com.project.lendmate.dto.requestDto.ProductCommentRequest;
import com.project.lendmate.dto.responseDto.ProductCommentResponse;
import com.project.lendmate.service.ProductCommentService;
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

    @GetMapping
    public ResponseEntity<List<ProductCommentResponse>> getComments(){
        return ResponseEntity.ok()
                .body(productCommentService.getComments());
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