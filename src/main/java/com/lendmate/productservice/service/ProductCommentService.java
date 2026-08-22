package com.lendmate.productservice.service;

import com.lendmate.productservice.dto.requestDto.ProductCommentRequest;
import com.lendmate.productservice.dto.responseDto.ProductCommentResponse;

import java.util.List;

public interface ProductCommentService {
    List<ProductCommentResponse> getComments(Long id);
    ProductCommentResponse createProductComment(ProductCommentRequest request);
    ProductCommentResponse updateProductComment(Long commentId, ProductCommentRequest request);
    void deleteProductComments(List<Long> commentIds);
}