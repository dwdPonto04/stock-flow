package com.dwdponto04.stockflow.business.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PutCategoryRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 20,message = "O nome da categoria deve ter no máximo 20 caracteres")
        String name) {
}

