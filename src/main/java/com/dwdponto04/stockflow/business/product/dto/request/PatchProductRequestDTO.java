package com.dwdponto04.stockflow.business.product.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record PatchProductRequestDTO(

        @Size(max = 70)
                String name,

        @Size(max = 500)
        String description,

        @Positive
        @Digits(integer = 8, fraction = 2)
        BigDecimal price,

        @PositiveOrZero
        Integer quantity,

        Long categoryId
) {
}
