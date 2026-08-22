package com.lendmate.productservice.mapper;

import com.lendmate.productservice.dto.requestDto.LocationRequest;
import com.lendmate.productservice.dto.responseDto.LocationResponse;
import com.lendmate.productservice.model.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {

    public Location toEntity(LocationRequest request) {
        if (request == null) return null;

        return Location.builder()
                .productId(request.getProductId())
                .city(request.getCity())
                .district(request.getDistrict())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .build();
    }

    public LocationResponse toDto(Location model) {
        if (model == null) return null;

        return LocationResponse.builder()
                .id(model.getId())
                .productId(model.getProductId())
                .city(model.getCity())
                .district(model.getDistrict())
                .latitude(model.getLatitude())
                .longitude(model.getLongitude())
                .build();
    }
}