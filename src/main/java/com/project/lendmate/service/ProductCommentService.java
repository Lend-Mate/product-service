package com.project.lendmate.service;

import com.project.lendmate.dto.requestDto.ProductCommentRequest;
import com.project.lendmate.dto.responseDto.ProductCommentResponse;

import java.util.List;

public interface ProductCommentService {
    List<ProductCommentResponse> getComments();
    ProductCommentResponse createProductComment(ProductCommentRequest request);
    ProductCommentResponse updateProductComment(Long commentId, ProductCommentRequest request);
    void deleteProductComments(List<Long> commentIds);
}