package com.lendmate.productservice.service.impl;

import com.lendmate.productservice.dto.responseDto.ProductImageResponse;
import com.lendmate.productservice.expection.ProductNotFoundException;
import com.lendmate.productservice.mapper.ProductImageMapper;
import com.lendmate.productservice.model.Product;
import com.lendmate.productservice.model.ProductImage;
import com.lendmate.productservice.repository.ProductImageRepository;
import com.lendmate.productservice.service.ProductImageService;
import com.lendmate.productservice.service.ProductService;
import com.lendmate.productservice.service.StorageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ProductImageServiceImpl implements ProductImageService {
    private final StorageService storageService;
    private final ProductImageMapper mapper;
    private final ProductImageRepository imageRepository;
    private final ProductService productService;

    //TODO: buraya isPrimary kontrolü eklenecek!!!!
    @Override
    public List<ProductImageResponse> createProductImage(Long productId, List<String> imageNames) {
        boolean existProduct = productService.existsProduct(productId);
        if (!existProduct) throw new ProductNotFoundException("Product not found productId: " + productId);
        return imageNames.stream()
                .map(imageName -> {
                    ProductImage image = ProductImage.builder()
                            .product(Product.builder()
                                    .id(productId)
                                    .build())
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
