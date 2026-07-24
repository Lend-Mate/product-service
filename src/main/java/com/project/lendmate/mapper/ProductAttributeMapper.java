package com.project.lendmate.mapper;

import com.project.lendmate.dto.requestDto.ProductAttributeRequest;
import com.project.lendmate.dto.responseDto.ProductAttributeResponse;
import com.project.lendmate.model.ProductAttribute;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductAttributeMapper {
    ProductAttribute toEntity(ProductAttributeRequest requestDto);

    ProductAttributeResponse toResponseDto(ProductAttribute notification);
}