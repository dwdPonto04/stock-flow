package com.dwdponto04.stockflow.business.product.mapper;

import com.dwdponto04.stockflow.business.category.mapper.CategoryMapper;
import com.dwdponto04.stockflow.business.product.dto.request.CreateProductRequestDTO;
import com.dwdponto04.stockflow.business.product.dto.response.ProductResponseDTO;
import com.dwdponto04.stockflow.business.product.entity.Product;

public class ProductMapper {

    public static Product toProduct (CreateProductRequestDTO createProductRequestDTO){
        Product product = new Product();
        product.setName(createProductRequestDTO.name());
        product.setDescription(createProductRequestDTO.description());
        product.setPrice(createProductRequestDTO.price());
        product.setQuantity(createProductRequestDTO.quantity());
        return product;
    }

    public static ProductResponseDTO toResponseProduct(Product product){

        return new ProductResponseDTO(
        product.getId(),
        product.getCode(),
        product.getName(),
        product.getDescription(),
        product.getPrice(),
        product.getQuantity(),
        CategoryMapper.toCategoryResponse(product.getCategory())
        );

    }
}
