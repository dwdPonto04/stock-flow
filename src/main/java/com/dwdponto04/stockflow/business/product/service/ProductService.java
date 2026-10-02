package com.dwdponto04.stockflow.business.product.service;


import com.dwdponto04.stockflow.business.category.entity.Category;
import com.dwdponto04.stockflow.business.product.dto.request.CreateProductRequestDTO;
import com.dwdponto04.stockflow.business.product.dto.request.PatchProductRequestDTO;
import com.dwdponto04.stockflow.business.product.dto.request.PutProductRequestDTO;
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
        String name = normalizeName(createProductRequestDTO.name());
        validateName(name);
        Category category = findCategoryById(createProductRequestDTO.categoryId());
        Product product = ProductMapper.toProduct(createProductRequestDTO);
        product.setName(name);
        product.setCategory(category);
        product.setCode(UUID.randomUUID());

        Product productSaved = productRepository.save(product);
        return ProductMapper.toResponseProduct(productSaved);

    }

    public ProductResponseDTO findByName(String name) {
        String normalizedName = normalizeName(name);
        Product product = productRepository.findByNameIgnoreCase(normalizedName)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Produto não encontrado no sistema"));
        return ProductMapper.toResponseProduct(product);

    }

    public List<ProductResponseDTO> findByCategoryId(Long categoryId){
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

    public ProductResponseDTO updateWithPatch(Long id, PatchProductRequestDTO patchDTO){

        if (patchDTO.name() == null &&
                patchDTO.description() == null &&
                patchDTO.price() == null &&
                patchDTO.quantity() == null &&
                patchDTO.categoryId() == null) {

            throw new IllegalArgumentException(
                    "É necessário informar ao menos um campo para atualização"
            );
        }
        Product product = findProductById(id);

        if(patchDTO.name() != null){
            String name = normalizeName(patchDTO.name());
            if(name.isEmpty()){
                throw new IllegalArgumentException("Nome do produto não pode ser vazio");
            }
            product.setName(name);
        }
        if (patchDTO.description() != null){
            product.setDescription(patchDTO.description());
        }
        if (patchDTO.price() != null){
            product.setPrice(patchDTO.price());
        }
        if (patchDTO.quantity() != null){
            product.setQuantity(patchDTO.quantity());
        }
        if (patchDTO.categoryId() != null){
            product.setCategory(categoryRepository.findById(patchDTO.categoryId()).orElseThrow(
                    () -> new ResourceNotFoundException("Categoria de produto não identificada")
            ));
        }


        Product updateProduct = productRepository.save(product);
        return ProductMapper.toResponseProduct(updateProduct);

    }


    public ProductResponseDTO updateWithPut(Long id, PutProductRequestDTO putDTO){

        Product product = findProductById(id);
        String name = normalizeName(putDTO.name());

        product.setName(name);
        product.setPrice(putDTO.price());
        product.setDescription(putDTO.description());
        product.setQuantity(putDTO.quantity());
        product.setCategory(findCategoryById(putDTO.categoryId()));

        Product updatedProduct = productRepository.save(product);
        return ProductMapper.toResponseProduct(updatedProduct);
    }

    public void delete(Long id){
        Product product = findProductById(id);
        productRepository.delete(product);
    }

    private Category findCategoryById(Long categoryId){
        return categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Categoria não encontrada")
        );
    }

    private Product findProductById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado")
                );
    }


    private String normalizeName(String name) {
        return name.trim();
    }

    private void validateName(String name) {
        if (productRepository.existsByNameIgnoreCase(name)) {
            throw new ConflictException("Esse produto já esta cadastrado");
        }
    }

}
