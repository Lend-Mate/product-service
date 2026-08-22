package com.lendmate.productservice.dto.requestDto;

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
public class ProductAttributeRequest {
    private Long productId;

    @NotNull(message = "Attribute name is mandatory")
    private String attributeName;

    @NotNull(message = "Attribute value is mandatory")
    private String attributeValue;

    private LocalDateTime createdAt;

}
