package com.lendmate.productservice.dto.responseDto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCommentResponse {

    private Long id;

    private Long productId;

    private Long userId;

    private String text;

    private int rating;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}