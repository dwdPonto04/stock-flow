package com.dwdponto04.stockflow.business.category.service;

import com.dwdponto04.stockflow.business.category.dto.request.CreateCategoryRequestDTO;
import com.dwdponto04.stockflow.business.category.dto.response.CategoryResponseDTO;
import com.dwdponto04.stockflow.business.category.entity.Category;
import com.dwdponto04.stockflow.business.category.mapper.CategoryMapper;
import com.dwdponto04.stockflow.infrastructure.exceptions.ConflictException;
import com.dwdponto04.stockflow.infrastructure.exceptions.ResourceNotFoundException;
import com.dwdponto04.stockflow.infrastructure.persistence.category.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public CategoryResponseDTO findByName(String name){
        String nameNormalized = name.trim();

        Category category = categoryRepository.findByNameIgnoreCase(nameNormalized).orElseThrow(()
        -> new ResourceNotFoundException ("Categoria não encontrada"));

        return CategoryMapper.toCategoryResponse(category);

    }

    public CategoryResponseDTO findById(Long id){
        Category category = findCategoryById(id);
        return CategoryMapper.toCategoryResponse(category);
    }

    public List<CategoryResponseDTO> findAll(){
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toCategoryResponse)
                .toList();
    }

    private Category findCategoryById(Long id){
        if (id == null || id <= 0){
            throw new IllegalArgumentException("ID inválido");
        }
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada")
                );
        return category;
    }

    private void validateNameNotExists(String name){
        if (categoryRepository.existsByNameIgnoreCase(name)){
            throw new ConflictException("Categoria já cadastrada");
        }
    }
}
