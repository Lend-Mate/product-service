package com.lendmate.productservice.service;

import com.lendmate.productservice.dto.requestDto.LocationRequest;
import com.lendmate.productservice.dto.responseDto.LocationResponse;

public interface LocationService {
    LocationResponse createLocation(LocationRequest request);
}