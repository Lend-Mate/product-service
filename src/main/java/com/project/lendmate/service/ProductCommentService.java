package com.project.lendmate.service;

import com.project.lendmate.dto.requestDto.ProductCommentRequest;
import com.project.lendmate.dto.responseDto.ProductCommentResponse;

public interface ProductCommentService {
    ProductCommentResponse createProductComment(ProductCommentRequest request);
}