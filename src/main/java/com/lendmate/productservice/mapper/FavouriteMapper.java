package com.lendmate.productservice.mapper;

import com.lendmate.productservice.dto.requestDto.FavouriteRequest;
import com.lendmate.productservice.dto.responseDto.FavouriteResponse;
import com.lendmate.productservice.model.Favourite;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class FavouriteMapper {
    private final ProductMapper productMapper;

    public Favourite toEntity(FavouriteRequest request) {
        if (request == null) {
            return null;
        }
        return Favourite.builder()
                .productId(request.getProductId())
                .userId(request.getUserId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public FavouriteResponse toDto(Favourite favourite) {
        if (favourite == null) {
            return null;
        }
        return FavouriteResponse.builder()
                .id(favourite.getId())
                .productId(favourite.getProductId())
                .userId(favourite.getUserId())
                .product(productMapper.toDto(favourite.getProduct()))
                .createdAt(favourite.getCreatedAt())
                .updatedAt(favourite.getUpdatedAt())
                .build();
    }

    public void updateEntity(Favourite entity, FavouriteRequest request) {
        if (request.getProductId() != null) {
            entity.setProductId(request.getProductId());
        }
        if (request.getUserId() != null) {
            entity.setUserId(request.getUserId());
        }
        entity.setUpdatedAt(LocalDateTime.now());
    }
}
