package com.project.lendmate.controller;

import com.project.lendmate.dto.requestDto.LocationRequest;
import com.project.lendmate.dto.responseDto.LocationResponse;
import com.project.lendmate.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/location")
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<LocationResponse> createLocation(
            @RequestBody LocationRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(locationService.createLocation(request));
    }
}