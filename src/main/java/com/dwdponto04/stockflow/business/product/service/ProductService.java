package com.dwdponto04.stockflow.business.product.service;


import com.dwdponto04.stockflow.business.category.entity.Category;
import com.dwdponto04.stockflow.business.product.dto.request.CreateProductRequestDTO;
import com.dwdponto04.stockflow.business.product.dto.response.ProductResponseDTO;
import com.dwdponto04.stockflow.business.product.entity.Product;
import com.dwdponto04.stockflow.business.product.mapper.ProductMapper;
import com.dwdponto04.stockflow.infrastructure.exceptions.ConflictException;
import com.dwdponto04.stockflow.infrastructure.exceptions.ResourceNotFoundException;
import com.dwdponto04.stockflow.infrastructure.persistence.category.CategoryRepository;
import com.dwdponto04.stockflow.infrastructure.persistence.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    public ProductResponseDTO createProduct(CreateProductRequestDTO createProductRequestDTO) {
        String name = normalizedName(createProductRequestDTO.name());
        validateName(name);
        Category category = categoryRepository.findById(
                createProductRequestDTO.categoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Categoria não encontrada")
        );


        Product product = ProductMapper.toProduct(createProductRequestDTO);
        product.setName(name);
        product.setCategory(category);
        product.setCode(UUID.randomUUID());

        Product productSaved = productRepository.save(product);

        return ProductMapper.toResponseProduct(productSaved);

    }

    public ProductResponseDTO findByName(String name) {
        String normalizedName = normalizedName(name);
        Product product = productRepository.findByNameIgnoreCase(normalizedName)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Produto não encontrado no sistema"));
        return ProductMapper.toResponseProduct(product);

    }

    public List<ProductResponseDTO> findByCategoryName(Long categoryId){
        return productRepository
                .findByCategoryId(categoryId)
                .stream()
                .map(ProductMapper::toResponseProduct)
                .toList();
    }


    public List<ProductResponseDTO> findAll(){
        return productRepository
                .findAll()
                .stream()
                .map(ProductMapper::toResponseProduct)
                .toList();
    }

    public ProductResponseDTO findByCode(UUID code) {
        Product product = productRepository.findByCode(code).orElseThrow(
                () -> new ResourceNotFoundException("Produto não encontrado")
        );
        return ProductMapper.toResponseProduct(product);
    }


    private String normalizedName(String name) {
        return name.trim();
    }

    private void validateName(String name) {
        if (productRepository.existsByNameIgnoreCase(name)) {
            throw new ConflictException("Esse produto já esta cadastrado");
        }
    }

}
