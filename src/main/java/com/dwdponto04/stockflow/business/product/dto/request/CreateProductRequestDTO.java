package com.dwdponto04.stockflow.business.product.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;


public record CreateProductRequestDTO(

        @NotBlank(message = "O nome do produto é obrigatório")
        @Size(max = 70,message = "Tamanho máximo para o nome do produto é de 70 caracteres")
        String name,

        @Size(max = 500)
        String description,

        @NotNull(message = "O preço do produto é obrigatório")
        @Positive(message = "O preço do produto deve ser positivo")
        @Digits(integer = 10,fraction = 2)
        BigDecimal price,

        @PositiveOrZero(message = "A quantidade de produtos não pode ser negativa")
        Integer quantity,

        @NotNull(message ="A categoria do produto é obrigatória")
        Long categoryId



) {
}
