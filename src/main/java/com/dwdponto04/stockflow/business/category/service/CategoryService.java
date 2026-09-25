package com.dwdponto04.stockflow.business.category.service;

import com.dwdponto04.stockflow.business.category.dto.request.CreateCategoryRequestDTO;
import com.dwdponto04.stockflow.business.category.dto.request.PutCategoryRequestDTO;
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

    public CategoryResponseDTO createCategory(CreateCategoryRequestDTO createCategoryRequestDTO) {
        String name = normalizedName(createCategoryRequestDTO.name());
        validateNameNotExists(name);

        CreateCategoryRequestDTO categoryRequestDTO =
                new CreateCategoryRequestDTO(
                        name
                );
        Category category = CategoryMapper.toCategory(categoryRequestDTO);

        Category savedCategory = categoryRepository.save(category);

        return CategoryMapper.toCategoryResponse(savedCategory);
    }

    public CategoryResponseDTO findByName(String name) {
        String newname = normalizedName(name);

        Category category = categoryRepository.findByNameIgnoreCase(newname).orElseThrow(()
                -> new ResourceNotFoundException("Categoria não encontrada"));

        return CategoryMapper.toCategoryResponse(category);

    }

    public CategoryResponseDTO findById(Long id) {
        Category category = findCategoryById(id);
        return CategoryMapper.toCategoryResponse(category);
    }

    public List<CategoryResponseDTO> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toCategoryResponse)
                .toList();
    }

    public CategoryResponseDTO updateWithPut(Long id,
                                             PutCategoryRequestDTO putCategoryRequestDTO) {
        Category category = findCategoryById(id);
        String name = normalizedName(putCategoryRequestDTO.name());
        validateNameNotExistForAnotherCategory(name, id);

        category.setName(name);
        categoryRepository.save(category);

        return CategoryMapper.toCategoryResponse(category);

    }

    public void delete(Long id) {
        Category category = findCategoryById(id);
        categoryRepository.delete(category);
    }

    private Category findCategoryById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada")
                );
        return category;
    }

    private void validateNameNotExistForAnotherCategory(String name, Long id) {
        categoryRepository.findByNameIgnoreCase(name)
                .ifPresent(category -> {
                    if (!category.getId().equals(id)) {
                        throw new ConflictException("Essa categoria já esta cadastrada");
                    }

                });
    }

    private String normalizedName(String name) {
        return name.trim();
    }

    private void validateNameNotExists(String name) {
        if (categoryRepository.existsByNameIgnoreCase(name)) {
            throw new ConflictException("Categoria já cadastrada");
        }
    }
}
