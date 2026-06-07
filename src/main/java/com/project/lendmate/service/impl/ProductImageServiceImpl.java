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
import com.project.lendmate.repository.ProductImageRepository;
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
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ProductImageServiceImpl implements ProductImageService {
    private final StorageService storageService;
    private final ProductImageMapper mapper;
    private final ProductImageRepository imageRepository;

    //TODO: buraya isPrimary kontrolü eklenecek!!!!
    @Override
    public List<ProductImageResponse> createProductImage(Long productId, List<String> imageNames) {
        return imageNames.stream()
                .map(imageName -> {
                    ProductImage image = ProductImage.builder()
                            .productId(productId)
                            .imageUrl(imageName)
                            .isPrimary(false)
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build();
                    return mapper.toDto(imageRepository.save(image));
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProductImages(List<Long> imageIds) {
        List<ProductImage> images = imageRepository.findAllById(imageIds);
        //images.forEach(image -> storageService.deleteFile(image.getImageUrl()));
        if (!images.isEmpty()){
            imageRepository.deleteAll(images);
        }
    }




}
