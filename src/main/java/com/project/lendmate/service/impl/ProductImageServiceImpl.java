package com.project.lendmate.service.impl;

import com.project.lendmate.dto.requestDto.ProductRequest;
import com.project.lendmate.dto.responseDto.ProductImageResponse;
import com.project.lendmate.dto.responseDto.ProductResponse;
import com.project.lendmate.expection.ProductAlreadyExistsException;
import com.project.lendmate.expection.ProductNotFoundException;
import com.project.lendmate.mapper.ProductImageMapper;
import com.project.lendmate.mapper.ProductMapper;
import com.project.lendmate.model.Product;
import com.project.lendmate.model.ProductImage;
import com.project.lendmate.repository.ProductRepository;
import com.project.lendmate.service.ProductImageService;
import com.project.lendmate.service.ProductService;
import com.project.lendmate.service.StorageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProductImageServiceImpl implements ProductImageService {
    private final StorageService storageService;
    private final ProductImageMapper mapper;

    @Override
    public ProductImageResponse createProductImage(MultipartFile file) {
        String fileName = storageService.uploadFile(file);
        ProductImage image = new ProductImage();
        image.setImageUrl(fileName);

        return mapper.toDto(image);
    }
}
