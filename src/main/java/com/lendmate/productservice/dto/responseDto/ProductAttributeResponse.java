package com.lendmate.productservice.dto.responseDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductAttributeResponse {
    private Long id;

    private Long productId;

    private String attributeName;

    private String attributeValue;

    private LocalDateTime createdAt;
}
