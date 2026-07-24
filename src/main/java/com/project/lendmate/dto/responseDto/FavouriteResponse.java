package com.project.lendmate.dto.responseDto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavouriteResponse {
    private Long id;
    private Long productId;
    private Long userId;
    private ProductResponse productResponse;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
