package com.arthurticianeli.petsfriends.applications.category;

import com.arthurticianeli.petsfriends.applications.category.domain.dtos.CategoryRequestDto;
import com.arthurticianeli.petsfriends.applications.category.domain.dtos.CategoryResponseDto;
import com.arthurticianeli.petsfriends.applications.category.domain.entities.CategoryEntity;
import com.arthurticianeli.petsfriends.applications.category.domain.service.ICategoryService;
import com.arthurticianeli.petsfriends.applications.category.infra.repository.CategoryRepository;
import com.arthurticianeli.petsfriends.applications.pets.domain.entities.PetEntity;
import com.arthurticianeli.petsfriends.applications.pets.infra.repository.PetRepository;
import com.arthurticianeli.petsfriends.exceptions.DuplicatedTupleException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;

    @Autowired
    private final PetRepository petRepository;

    @Transactional
    public CategoryResponseDto saveCategory(CategoryRequestDto categoryRequestDto) {
        var category = new CategoryEntity(categoryRequestDto);

        var possibleCategory = categoryRepository.findByName(category.getName());
        if (possibleCategory != null) {
            throw new DuplicatedTupleException("Category already exists");
        }

        return new CategoryResponseDto(categoryRepository.save(category));
    }


    @Override
    @Transactional
    public void deleteCategory(Long id) {
        List<PetEntity> pets = petRepository.findByCategoryId(id);
        for (PetEntity pet : pets) {
            pet.setCategory(null);
            petRepository.save(pet);
        }

        categoryRepository.deleteById(id);
    }
    @Override
    public Page<CategoryResponseDto> findAllCategoriesPaginated(Pageable pageable) {

        Page<CategoryEntity> categories = categoryRepository.findAll(pageable);

        List<CategoryResponseDto> categoriesResponse = categories.map(CategoryResponseDto::new).toList();

        return new PageImpl<>(
                categoriesResponse,
                pageable,
                categories.getTotalElements()
        );

    }

    @Override
    @Transactional
    public List<CategoryResponseDto> getAllCategories() {
        return CategoryResponseDto.from(categoryRepository.findAll());
    }

}
