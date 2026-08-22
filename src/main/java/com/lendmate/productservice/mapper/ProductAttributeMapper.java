package com.lendmate.productservice.mapper;

import com.lendmate.productservice.dto.requestDto.ProductAttributeRequest;
import com.lendmate.productservice.dto.responseDto.ProductAttributeResponse;
import com.lendmate.productservice.model.ProductAttribute;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductAttributeMapper {
    ProductAttribute toEntity(ProductAttributeRequest requestDto);

    ProductAttributeResponse toResponseDto(ProductAttribute notification);
}