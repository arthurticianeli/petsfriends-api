package com.arthurticianeli.petsfriends.applications.authentication.service;

import com.arthurticianeli.petsfriends.applications.authentication.domain.AccessToken;
import com.arthurticianeli.petsfriends.applications.authentication.domain.SecretKeyGenerator;
import com.arthurticianeli.petsfriends.applications.authentication.domain.dtos.UserRequestDto;
import com.arthurticianeli.petsfriends.applications.authentication.domain.entity.User;
import com.arthurticianeli.petsfriends.exceptions.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @Mock
    private SecretKeyGenerator keyGenerator;

    @InjectMocks
    private JwtService jwtService;

    private Key key;
    private User user;

    @BeforeEach
    void setUp() {
        byte[] keyBytes = new byte[256 / 8];
        String algorithm = "HmacSHA256";
        key = new SecretKeySpec(keyBytes, algorithm);

        UserRequestDto userRequestDto = new UserRequestDto(
                "Arthur",
                "arthur@email.com",
                "123456"
        );
        user = new User(userRequestDto);
    }

    @Test
    void generateToken_ShouldReturnValidToken() {
        when(keyGenerator.getKey()).thenReturn((SecretKey) key);

        AccessToken token = jwtService.generateToken(user);
        assertNotNull(token);

        Jws<Claims> claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(token.accessToken());
        assertEquals(user.getEmail(), claims.getBody().getSubject());
        assertEquals(user.getName(), claims.getBody().get("name"));
    }

    @Test
    void getEmailFromToken_ShouldReturnEmail() {
        when(keyGenerator.getKey()).thenReturn((SecretKey) key);

        AccessToken token = jwtService.generateToken(user);
        String email = jwtService.getEmailFromToken(token.accessToken());

        assertEquals(user.getEmail(), email);
    }

    @Test
    void getEmailFromToken_WithInvalidToken_ShouldThrowException() {
        when(keyGenerator.getKey()).thenReturn((SecretKey) key);

        assertThrows(InvalidTokenException.class, () -> jwtService.getEmailFromToken("invalid.token"));
    }
}
