package com.project.lendmate.service.impl;

import com.project.lendmate.dto.requestDto.LocationRequest;
import com.project.lendmate.dto.responseDto.LocationResponse;
import com.project.lendmate.mapper.LocationMapper;
import com.project.lendmate.model.Location;
import com.project.lendmate.repository.LocationRepository;
import com.project.lendmate.service.LocationService;
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