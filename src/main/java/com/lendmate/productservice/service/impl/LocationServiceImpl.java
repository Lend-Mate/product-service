package com.lendmate.productservice.service.impl;

import com.lendmate.productservice.dto.requestDto.LocationRequest;
import com.lendmate.productservice.dto.responseDto.LocationResponse;
import com.lendmate.productservice.mapper.LocationMapper;
import com.lendmate.productservice.model.Location;
import com.lendmate.productservice.repository.LocationRepository;
import com.lendmate.productservice.service.LocationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {

    private final LocationRepository repository;
    private final LocationMapper mapper;

    @Override
    public LocationResponse createLocation(LocationRequest request) {
        Location model = mapper.toEntity(request);
        Location saved = repository.save(model);
        return mapper.toDto(saved);
    }
}