package com.project.lendmate.dto.responseDto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCommentResponse {

    private Long id;

    private int productId;

    private String text;

    private int rating;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}