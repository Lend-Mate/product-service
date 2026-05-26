package com.project.lendmate.dto.requestDto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCommentRequest {

    @NotNull(message = "Product id is mandatory")
    private int productId;

    @NotNull(message = "Text is mandatory")
    private String text;

    @NotNull(message = "Rating is mandatory")
    private int rating;
}