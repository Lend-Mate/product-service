package com.project.lendmate.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {

    @NotBlank(message = "Kategori adı zorunludur")
    @Size(max = 255, message = "Kategori adı 255 karakterden uzun olamaz")
    private String categoryName;

    @Size(max = 500, message = "Açıklama 500 karakterden uzun olamaz")
    private String description;

    private Boolean isActive;
}
