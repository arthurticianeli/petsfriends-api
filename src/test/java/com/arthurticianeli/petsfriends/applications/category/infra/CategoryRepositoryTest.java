package com.arthurticianeli.petsfriends.applications.category.infra;

import com.arthurticianeli.petsfriends.applications.category.domain.entities.CategoryEntity;
import com.arthurticianeli.petsfriends.applications.category.infra.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void findByName_ShouldReturnCategory_WhenCategoryExists() {
        CategoryEntity newCategory = new CategoryEntity();
        newCategory.setName("test");

        entityManager.persist(newCategory);
        entityManager.flush();

        CategoryEntity foundCategory = categoryRepository.findByName(newCategory.getName());

        assertThat(foundCategory).isNotNull();
        assertThat(foundCategory.getName()).isEqualTo(newCategory.getName());
    }
}
