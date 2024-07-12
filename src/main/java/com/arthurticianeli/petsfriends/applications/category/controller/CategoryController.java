package com.arthurticianeli.petsfriends.applications.category.controller;

import com.arthurticianeli.petsfriends.applications.category.service.CategoryServiceImpl;
import com.arthurticianeli.petsfriends.applications.category.domain.dtos.CategoryRequestDto;
import com.arthurticianeli.petsfriends.applications.category.domain.dtos.CategoryResponseDto;
import com.arthurticianeli.petsfriends.applications.category.infra.repository.CategoryRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private CategoryServiceImpl service;

    @PostMapping
    public ResponseEntity<CategoryResponseDto> saveCategory(
            @RequestBody @Valid
            CategoryRequestDto categoryRequestDto,
            UriComponentsBuilder uriComponentsBuilder
    ) {

        var category = service.saveCategory(categoryRequestDto);

        var uri = uriComponentsBuilder.path("/v1/category/{id}").buildAndExpand(category.id()).toUri();

        return ResponseEntity.created(uri).body(category);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        service.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list")
    public ResponseEntity<Page<CategoryResponseDto>> findAllCategoriesByFilterAndPagination(Pageable pageable) {
        return ResponseEntity.ok(service.findAllCategoriesPaginated(pageable));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        return ResponseEntity.ok(service.getAllCategories());
    }

}
