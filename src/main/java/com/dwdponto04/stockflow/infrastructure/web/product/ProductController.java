package com.dwdponto04.stockflow.infrastructure.web.product;

import com.dwdponto04.stockflow.business.product.dto.request.CreateProductRequestDTO;
import com.dwdponto04.stockflow.business.product.dto.request.PatchProductRequestDTO;
import com.dwdponto04.stockflow.business.product.dto.request.PutProductRequestDTO;
import com.dwdponto04.stockflow.business.product.dto.response.ProductResponseDTO;
import com.dwdponto04.stockflow.business.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(
            @Valid @RequestBody
            CreateProductRequestDTO productRequestDTO) {

        ProductResponseDTO productResponseDTO = productService.createProduct(productRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(productResponseDTO);
    }

    @GetMapping("/name")
    public ResponseEntity<ProductResponseDTO> findByName(
            @RequestParam
            String name) {
        ProductResponseDTO productResponseDTO = productService.findByName(name);
        return ResponseEntity.ok(productResponseDTO);
    }

    @GetMapping("/{code}")
    public ResponseEntity<ProductResponseDTO> findByCode(@PathVariable UUID code) {
        ProductResponseDTO productResponseDTO = productService.findByCode(code);
        return ResponseEntity.ok(productResponseDTO);
    }

    @GetMapping("/category")
    public ResponseEntity<List<ProductResponseDTO>> findByCategoryId(
            @RequestParam
            Long categoryId) {
        List<ProductResponseDTO> products =
                productService.findByCategoryId((categoryId)
                );
        return ResponseEntity.ok(products);
    }

    @GetMapping("/name/partial")
    public ResponseEntity<List<ProductResponseDTO>> findPartialName(@RequestParam String name){
        List<ProductResponseDTO> products = productService.findByPartialName(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> findAllProducts(){
        List<ProductResponseDTO> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateWithPatch(
            @PathVariable Long id,
            @Valid @RequestBody
            PatchProductRequestDTO productRequestDTO
    ){
        ProductResponseDTO responseDTO = productService.updateWithPatch(id,productRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateWithPut(
            @PathVariable Long id,
            @Valid @RequestBody
            PutProductRequestDTO productRequestDTO){
        ProductResponseDTO productResponseDTO = productService.updateWithPut(id,productRequestDTO);
        return ResponseEntity.ok(productResponseDTO);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
