package com.arthurticianeli.petsfriends.applications.authentication.domain;

import com.arthurticianeli.petsfriends.applications.authentication.domain.dtos.UserRequestDto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRequestDtoTest {

    @Test
    public void testRecordAttributes() {
        String name = "John Doe";
        String email = "john.doe@example.com";
        String password = "password123";
        UserRequestDto dto = new UserRequestDto(name, email, password);

        assertEquals(name, dto.name());
        assertEquals(email, dto.email());
        assertEquals(password, dto.password());
    }
}