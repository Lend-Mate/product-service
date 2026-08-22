package com.lendmate.productservice.dto.requestDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavouriteRequest {

    @NotNull(message = "Product ID zorunludur")
    private Long productId;

    @NotNull(message = "User ID zorunludur")
    private Long userId;
}
