package com.dwdponto04.stockflow.business.product.dto.response;

import com.dwdponto04.stockflow.business.category.dto.response.CategoryResponseDTO;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponseDTO(
        Long  id,
        UUID code,
        String name,
        String description,
        BigDecimal prince,
        Integer quantity,
        CategoryResponseDTO category
) {
}
