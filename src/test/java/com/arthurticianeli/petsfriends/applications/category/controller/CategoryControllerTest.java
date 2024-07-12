package com.arthurticianeli.petsfriends.applications.category.controller;

import com.arthurticianeli.petsfriends.applications.category.domain.dtos.CategoryRequestDto;
import com.arthurticianeli.petsfriends.applications.category.domain.dtos.CategoryResponseDto;
import com.arthurticianeli.petsfriends.applications.category.domain.entities.CategoryEntity;
import com.arthurticianeli.petsfriends.applications.category.service.CategoryServiceImpl;
import com.arthurticianeli.petsfriends.exceptions.DuplicatedTupleException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    @Mock
    private CategoryServiceImpl categoryService;

    @InjectMocks
    private CategoryController categoryController;

    CategoryEntity categoryEntity = new CategoryEntity("Test Category");
    CategoryRequestDto categoryRequestDto = new CategoryRequestDto("Test Category");
    CategoryResponseDto categoryResponseDto = new CategoryResponseDto(categoryEntity);

    @Test
    public void saveCategory_WhenCategoryDoesNotExist_ShouldReturnCategory() throws Exception {

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Mockito.when(categoryService.saveCategory(any(CategoryRequestDto.class))).thenReturn(categoryResponseDto);

        ResponseEntity<CategoryResponseDto> responseEntity = categoryController.saveCategory(
                categoryRequestDto,
                uriComponentsBuilder
        );

        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals("Test Category", responseEntity.getBody().name());
    }

    @Test
    public void saveCategory_WhenCategoryAlreadyExists_ShouldThrowDuplicatedTupleException() throws Exception {

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Mockito.when(categoryService.saveCategory(any(CategoryRequestDto.class))).thenThrow(new DuplicatedTupleException("Category already exists"));

        Assertions.assertThrows(DuplicatedTupleException.class, () -> {
            categoryController.saveCategory(
                    categoryRequestDto,
                    uriComponentsBuilder
            );
        });
    }

    @Test
    public void getAllCategories_WhenThereAreNoCategories_ShouldReturnEmptyList() {
        Mockito.when(categoryService.getAllCategories()).thenReturn(Collections.emptyList());

        ResponseEntity<List<CategoryResponseDto>> responseEntity = categoryController.getAllCategories();

        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals(0, responseEntity.getBody().size());
    }

    @Test
    public void deleteCategory_WhenCategoryExists_ShouldDeleteCategory() {
        Mockito.doNothing().when(categoryService).deleteCategory(1L);

        ResponseEntity<Void> responseEntity = categoryController.deleteCategory(1L);

        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(204, responseEntity.getStatusCode().value());
    }

    @Test
    public void findAllCategoriesByFilterAndPagination_WhenThereAreNoCategories_ShouldReturnEmptyPage() {
        Page<CategoryResponseDto> page = new PageImpl<>(Collections.emptyList());
        Mockito.when(categoryService.findAllCategoriesPaginated(any())).thenReturn(page);

        ResponseEntity<Page<CategoryResponseDto>> responseEntity = categoryController
                .findAllCategoriesByFilterAndPagination(null);

        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals(0, responseEntity.getBody().getTotalElements());
    }

    @Test
    public void findAllCategoriesByFilterAndPagination_WhenThereAreCategories_ShouldReturnPage() {
        Page<CategoryResponseDto> page = new PageImpl<>(Collections.singletonList(categoryResponseDto));
        Mockito.when(categoryService.findAllCategoriesPaginated(any())).thenReturn(page);

        ResponseEntity<Page<CategoryResponseDto>> responseEntity = categoryController
                .findAllCategoriesByFilterAndPagination(null);

        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals(1, responseEntity.getBody().getTotalElements());
        Assertions.assertEquals("Test Category", responseEntity.getBody().getContent().get(0).name());
    }
}
