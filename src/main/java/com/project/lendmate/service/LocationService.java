package com.project.lendmate.service;

import com.project.lendmate.dto.requestDto.LocationRequest;
import com.project.lendmate.dto.responseDto.LocationResponse;

public interface LocationService {
    LocationResponse createLocation(LocationRequest request);
}