package com.project.lendmate.service;

import com.project.lendmate.dto.requestDto.ProductRequest;
import com.project.lendmate.dto.responseDto.ProductImageResponse;
import com.project.lendmate.dto.responseDto.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductImageService {
    List<ProductImageResponse> createProductImage(Long productId, List<MultipartFile> files);
    void deleteProductImages(List<Long> imageIds);
}
