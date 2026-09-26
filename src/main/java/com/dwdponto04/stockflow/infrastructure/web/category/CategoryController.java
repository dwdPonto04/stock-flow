package com.dwdponto04.stockflow.infrastructure.web.category;


import com.dwdponto04.stockflow.business.category.dto.request.CreateCategoryRequestDTO;
import com.dwdponto04.stockflow.business.category.dto.request.PutCategoryRequestDTO;
import com.dwdponto04.stockflow.business.category.dto.response.CategoryResponseDTO;
import com.dwdponto04.stockflow.business.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

@PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory (
            @Valid @RequestBody
            CreateCategoryRequestDTO createCategoryRequestDTO){

    CategoryResponseDTO categoryResponseDTO = categoryService
            .createCategory(createCategoryRequestDTO);

    return ResponseEntity.status(HttpStatus.CREATED)
            .body(categoryResponseDTO);
}
@GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> findCategoryById(
            @PathVariable Long id){

    CategoryResponseDTO categoryResponseDTO = categoryService.findById(id);
    return ResponseEntity.ok(categoryResponseDTO);
}

@GetMapping("/name")
    public ResponseEntity<CategoryResponseDTO> findCategoryByName(@RequestParam String name){
    CategoryResponseDTO categoryResponseDTO = categoryService.findByName(name);
    return ResponseEntity.ok(categoryResponseDTO);
}

@GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> findAllCategories(){
    List<CategoryResponseDTO> categories = categoryService.findAll();
    return ResponseEntity.ok(categories);
}

@PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(
            @PathVariable Long id,
            @RequestBody
            PutCategoryRequestDTO putCategoryRequestDTO){
    CategoryResponseDTO categoryResponseDTO = categoryService.updateWithPut(id,putCategoryRequestDTO);
    return ResponseEntity.ok(categoryResponseDTO);
}

@DeleteMapping("/{id}")
public ResponseEntity<Void>deleteCategory(@PathVariable Long id){
    categoryService.delete(id);
    return ResponseEntity.noContent().build();

}
}
