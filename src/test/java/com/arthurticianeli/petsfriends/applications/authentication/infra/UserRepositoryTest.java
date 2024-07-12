package com.arthurticianeli.petsfriends.applications.authentication.infra;

import com.arthurticianeli.petsfriends.applications.authentication.domain.entity.User;
import com.arthurticianeli.petsfriends.applications.authentication.infra.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByEmail_ShouldReturnUser_WhenUserExists() {
        User newUser = new User();
        newUser.setEmail("test@example.com");
        newUser.setPassword("password");
        newUser.setName("Test User");

        entityManager.persist(newUser);
        entityManager.flush();

        User foundUser = userRepository.findByEmail(newUser.getEmail());

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo(newUser.getEmail());
        assertThat(foundUser.getName()).isEqualTo(newUser.getName());

    }
}