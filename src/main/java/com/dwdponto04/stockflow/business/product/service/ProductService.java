package com.dwdponto04.stockflow.business.product.service;


import com.dwdponto04.stockflow.business.product.dto.request.CreateProductRequestDTO;
import com.dwdponto04.stockflow.business.product.dto.response.ProductResponseDTO;
import com.dwdponto04.stockflow.business.product.entity.Product;
import com.dwdponto04.stockflow.business.product.mapper.ProductMapper;
import com.dwdponto04.stockflow.infrastructure.exceptions.ConflictException;
import com.dwdponto04.stockflow.infrastructure.persistence.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponseDTO createProduct (CreateProductRequestDTO createProductRequestDTO){
            String name = normalizedName(createProductRequestDTO.name());
            validateName(name);

            CreateProductRequestDTO productRequestDTO = new CreateProductRequestDTO(
                    name,
                    createProductRequestDTO.description(),
                    createProductRequestDTO.price(),
                    createProductRequestDTO.quantity(),
                    createProductRequestDTO.categoryId()

            );

        Product product = ProductMapper.toProduct(productRequestDTO);
        product.setCode(UUID.randomUUID());

        Product productSaved = productRepository.save(product);

        return ProductMapper.toResponseProduct(productSaved);

    }

    private String normalizedName(String name){
        return  name.trim();
    }
    private void validateName(String name){
        if(productRepository.existsByNameIgnoreCase(name)){
            throw new ConflictException("Esse produto já esta cadastrado");
        }
    }

}
