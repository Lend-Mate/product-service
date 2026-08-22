package com.lendmate.productservice.dto.requestDto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationRequest {

    @NotNull(message = "Product id is mandatory")
    private int productId;

    @NotNull(message = "City is mandatory")
    private String city;

    @NotNull(message = "District is mandatory")
    private String district;

    @NotNull(message = "Latitude is mandatory")
    private double latitude;

    @NotNull(message = "Longitude is mandatory")
    private double longitude;
}