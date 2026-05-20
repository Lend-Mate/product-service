package com.project.lendmate.dto.responseDto;

import com.project.lendmate.model.Enum.Reason;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductAvailabilityResponse {

    private Long id;

    private Long productId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Reason reason;

    private LocalDateTime createdAt;
}
