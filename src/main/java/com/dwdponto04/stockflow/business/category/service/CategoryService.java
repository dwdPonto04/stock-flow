package com.dwdponto04.stockflow.business.category.service;

import com.dwdponto04.stockflow.business.category.dto.request.CreateCategoryRequestDTO;
import com.dwdponto04.stockflow.business.category.dto.response.CategoryResponseDTO;
import com.dwdponto04.stockflow.business.category.entity.Category;
import com.dwdponto04.stockflow.business.category.mapper.CategoryMapper;
import com.dwdponto04.stockflow.infrastructure.exceptions.ConflictException;
import com.dwdponto04.stockflow.infrastructure.persistence.category.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponseDTO createCategory(CreateCategoryRequestDTO createCategoryRequestDTO){
        String name = createCategoryRequestDTO.name().trim();
        validateNameNotExists(name);

        CreateCategoryRequestDTO categoryRequestDTO =
                new CreateCategoryRequestDTO(
                        name
                );
        Category category = CategoryMapper.toCategory(categoryRequestDTO);

        Category savedCategory = categoryRepository.save(category);

        return CategoryMapper.toCategoryResponse(savedCategory);
    }



    private void validateNameNotExists(String name){
        if (categoryRepository.existsByName(name)){
            throw new ConflictException("Categoria já cadastrada");
        }
    }
}
