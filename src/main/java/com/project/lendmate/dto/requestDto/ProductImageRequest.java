package com.project.lendmate.dto.requestDto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageRequest {

    @NotNull(message = "Image url is mandatory")
    private String imageUrl;

    @NotNull(message = "Category id is mandatory")
    private Long categoryId;

    @NotBlank(message = "Product name is mandatory")
    @Size(max = 150, message = "Product name cannot exceed 150 characters")
    private String productName;
}