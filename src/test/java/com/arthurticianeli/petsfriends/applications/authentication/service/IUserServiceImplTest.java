package com.arthurticianeli.petsfriends.applications.authentication.service;

import com.arthurticianeli.petsfriends.applications.authentication.domain.AccessToken;
import com.arthurticianeli.petsfriends.applications.authentication.domain.dtos.UserRequestDto;
import com.arthurticianeli.petsfriends.applications.authentication.domain.entity.User;
import com.arthurticianeli.petsfriends.applications.authentication.infra.repository.UserRepository;
import com.arthurticianeli.petsfriends.exceptions.DuplicatedTupleException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IUserServiceImplTest {


    @Mock
    private UserRepository userRepository; // Mock UserRepository

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private IUserServiceImpl userService;

    UserRequestDto userRequestDto =
            new UserRequestDto(
                    "Test User",
                    "test@example.com",
                    "password"
            );
    private final User user = new User(userRequestDto);
    private AccessToken accessToken = new AccessToken("mockAccessToken");

    @Test
    void getByEmail() {
        when(userService.getByEmail(userRequestDto.email())).thenReturn(user);

        User user = userService.getByEmail(userRequestDto.email());

        assertNotNull(user);
        assertEquals(userRequestDto.email(), user.getEmail());
        assertEquals(userRequestDto.name(), user.getName());
        assertEquals(userRequestDto.password(), user.getPassword());
    }

    @Test
    void save() {
        when(userRepository.findByEmail(userRequestDto.email())).thenReturn(null);
        when(passwordEncoder.encode(userRequestDto.password())).thenReturn("encodedPassword");
        when(jwtService.generateToken(user)).thenReturn(accessToken);

        AccessToken response = userService.save(user);

        assertNotNull(response);
        assertEquals(accessToken, response);
    }

    @Test
    void save_WhenUserAlreadyExists_ShouldThrowException() {
        when(userRepository.findByEmail(userRequestDto.email())).thenReturn(user);

        Assertions.assertThrows(DuplicatedTupleException.class, () -> {
            userService.save(user);
        });
    }

    @Test
    void authenticate() {
        when(userRepository.findByEmail(userRequestDto.email())).thenReturn(user);
        when(passwordEncoder.matches(userRequestDto.password(), user.getPassword())).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn(accessToken);

        AccessToken response = userService.authenticate(userRequestDto.email(), userRequestDto.password());

        assertNotNull(response);
        assertEquals(accessToken, response);
    }

    @Test
    void authenticate_WhenUserDoesNotExist_ShouldReturnNull() {
        when(userRepository.findByEmail(userRequestDto.email())).thenReturn(null);

        AccessToken response = userService.authenticate(userRequestDto.email(), userRequestDto.password());

        Assertions.assertNull(response);
    }

    @Test
    void authenticate_WhenPasswordDoesNotMatch_ShouldReturnNull() {
        when(userRepository.findByEmail(userRequestDto.email())).thenReturn(user);
        when(passwordEncoder.matches(userRequestDto.password(), user.getPassword())).thenReturn(false);

        AccessToken response = userService.authenticate(userRequestDto.email(), userRequestDto.password());

        Assertions.assertNull(response);
    }

}