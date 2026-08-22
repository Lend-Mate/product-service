package com.lendmate.productservice.dto.requestDto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCommentRequest {

    @NotNull(message = "Product id is mandatory")
    private Long productId;

    @NotNull(message = "User id is mandatory")
    private Long userId;

    @NotNull(message = "Text is mandatory")
    private String text;

    @NotNull(message = "Rating is mandatory")
    @Min(1) @Max(5)
    private Integer rating;
}