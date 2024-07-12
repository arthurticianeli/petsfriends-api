package com.arthurticianeli.petsfriends.applications.category.service;

import com.arthurticianeli.petsfriends.applications.category.controller.CategoryController;
import com.arthurticianeli.petsfriends.applications.category.domain.dtos.CategoryRequestDto;
import com.arthurticianeli.petsfriends.applications.category.domain.dtos.CategoryResponseDto;
import com.arthurticianeli.petsfriends.applications.category.domain.entities.CategoryEntity;
import com.arthurticianeli.petsfriends.applications.category.domain.service.ICategoryService;
import com.arthurticianeli.petsfriends.applications.category.infra.repository.CategoryRepository;
import com.arthurticianeli.petsfriends.applications.pets.infra.repository.PetRepository;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    CategoryEntity categoryEntity = new CategoryEntity("Test Category");
    CategoryRequestDto categoryRequestDto = new CategoryRequestDto("Test Category");
    CategoryResponseDto categoryResponseDto = new CategoryResponseDto(categoryEntity);

    @Test
    public void saveCategory_WhenCategoryDoesNotExist_ShouldReturnCategory() throws Exception {
        Mockito.when(categoryRepository.findByName(categoryEntity.getName())).thenReturn(null);
        Mockito.when(categoryRepository.save(Mockito.any(CategoryEntity.class))).thenReturn(categoryEntity);

        CategoryResponseDto response = categoryService.saveCategory(categoryRequestDto);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(categoryResponseDto.name(), response.name());
    }

    @Test
    public void saveCategory_WhenCategoryExists_ShouldThrowException() throws Exception {
        Mockito.when(categoryRepository.findByName(categoryEntity.getName())).thenReturn(null);
        Mockito.when(categoryRepository.save(Mockito.any(CategoryEntity.class))).thenReturn(categoryEntity);

        Mockito.when(categoryService.saveCategory(categoryRequestDto)).thenThrow(new DuplicatedTupleException("Category already exists"));

        Assertions.assertThrows(DuplicatedTupleException.class, () -> {
            categoryService.saveCategory(categoryRequestDto);
        });
    }

    @Test
    public void deleteCategory_WhenCategoryExists_ShouldDeleteCategory() throws Exception {
        Long categoryId = 1L;
        categoryService.deleteCategory(categoryId);
        Mockito.verify(categoryRepository, Mockito.times(1)).deleteById(categoryId);
    }

    @Test
    public void findAllCategoriesPaginated_WhenCategoriesExist_ShouldReturnCategories() throws Exception {
        Mockito.when(categoryRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(categoryEntity)));

        Page<CategoryResponseDto> response = categoryService.findAllCategoriesPaginated(PageRequest.of(0, 10));
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.get().findFirst().get().name(), categoryResponseDto.name());

      }

    @Test
    public void findAllCategoriesPaginated_WhenCategoriesDoNotExist_ShouldReturnEmptyList() throws Exception {
        Mockito.when(categoryRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        Page<CategoryResponseDto> response = categoryService.findAllCategoriesPaginated(PageRequest.of(0, 10));
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.isEmpty());
    }

    @Test
    public void getAllCategories_WhenCategoriesExist_ShouldReturnCategories() throws Exception {
        Mockito.when(categoryRepository.findAll()).thenReturn(Collections.singletonList(categoryEntity));

        var response = categoryService.getAllCategories();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.get(0).name(), categoryResponseDto.name());
    }
}
