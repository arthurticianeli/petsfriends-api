package com.arthurticianeli.petsfriends.applications.authentication.domain;

import com.arthurticianeli.petsfriends.applications.authentication.domain.dtos.CrendentialsRequestDto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CrendentialsRequestDtoTest {

    @Test
    public void testRecordAttributes() {
        String email = "test@example.com";
        String password = "password123";
        CrendentialsRequestDto dto = new CrendentialsRequestDto(email, password);

        assertEquals(email, dto.email());
        assertEquals(password, dto.password());
    }
}