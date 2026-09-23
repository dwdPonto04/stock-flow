package com.dwdponto04.stockflow.business.category.mapper;

import com.dwdponto04.stockflow.business.category.dto.request.CreateCategoryRequestDTO;
import com.dwdponto04.stockflow.business.category.dto.response.CategoryResponseDTO;
import com.dwdponto04.stockflow.business.category.entity.Category;

public class CategoryMapper {

    public static Category toCategory(CreateCategoryRequestDTO createCategoryRequestDTO){
        Category category = new Category();

        category.setName(createCategoryRequestDTO.name());

        return category;
    }

    public static CategoryResponseDTO toCategoryResponse(Category category){
        return new CategoryResponseDTO(
                category.getId(),
                category.getName()
        );
    }
}
