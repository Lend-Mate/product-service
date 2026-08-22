package com.lendmate.productservice.dto.responseDto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponse {

    private Long id;

    private int productId;

    private String city;

    private String district;

    private double latitude;

    private double longitude;
}