package com.arthurticianeli.petsfriends.applications.category.infra.repository;

import com.arthurticianeli.petsfriends.applications.category.domain.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>, JpaSpecificationExecutor<CategoryEntity> {
    CategoryEntity findByName(String name);
}