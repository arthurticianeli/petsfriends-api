package com.arthurticianeli.petsfriends.applications.authentication.domain;

import com.arthurticianeli.petsfriends.applications.authentication.domain.dtos.UserRequestDto;
import com.arthurticianeli.petsfriends.applications.authentication.domain.entity.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("User Test")
public class UserTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Should create user valid with created at date")
    public void shouldCreateUserValid() {
        UserRequestDto userRequestDto = new UserRequestDto(
                "Arthur",
                "arthur@email.com",
                "123456");

        User user = new User(userRequestDto);

        Assertions.assertAll("User",
                () -> Assertions.assertEquals("Arthur", user.getName()),
                () -> Assertions.assertEquals("arthur@email.com", user.getEmail()),
                () -> Assertions.assertEquals("123456", user.getPassword()),
                () -> Assertions.assertNotNull(user.getCreatedAt())
        );

    }

    @Test
    @DisplayName("shoud not create user without name")
    public void shouldNotCreateUserWithoutName() {
        UserRequestDto dto = new UserRequestDto(null, "arthur@email.com", "123456");
        User user = new User(dto);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("shoud not create user without email")
    public void shouldNotCreateUserWithoutEmail() {
        UserRequestDto dto = new UserRequestDto("Arthur", null, "123456");
        User user = new User(dto);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty());

    }

    @Test
    @DisplayName("shoud not create user without password")
    public void shouldNotCreateUserWithoutPassword() {
        UserRequestDto dto = new UserRequestDto("Arthur", "arthur@email.com", null);
        User user = new User(dto);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("No arguments constructor")
    public void noArgsConstructor() {
        User user = new User();
        Assertions.assertNotNull(user);
    }

    @Test
    @DisplayName("Setters")
    public void setters() {
        User user = new User();
        user.setId(1L);
        user.setName("Arthur");
        user.setEmail("arthur@mail.com");
        user.setPassword("123456");
    }

    @Test
    @DisplayName("Equals and HashCode")
    public void equalsAndHashCode() {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("Arthur");
        user1.setEmail("arthur@mail.com");
        user1.setPassword("123456");

        User user2 = new User();
        user2.setId(1L);
        user2.setName("Arthur");
        user1.setEmail("arthur@mail.com");
        user1.setPassword("123456");

        Assertions.assertEquals(user1, user2);
        Assertions.assertEquals(user1.hashCode(), user2.hashCode());
    }
}
