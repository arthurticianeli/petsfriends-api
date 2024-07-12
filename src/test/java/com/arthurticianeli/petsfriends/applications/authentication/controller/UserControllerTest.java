package com.arthurticianeli.petsfriends.applications.authentication.controller;

import com.arthurticianeli.petsfriends.applications.authentication.domain.AccessToken;
import com.arthurticianeli.petsfriends.applications.authentication.domain.dtos.CrendentialsRequestDto;
import com.arthurticianeli.petsfriends.applications.authentication.domain.dtos.UserRequestDto;
import com.arthurticianeli.petsfriends.applications.authentication.domain.entity.User;
import com.arthurticianeli.petsfriends.applications.authentication.domain.services.IUserService;
import com.arthurticianeli.petsfriends.exceptions.DuplicatedTupleException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private IUserService userService;

    @InjectMocks
    private UserController userController;

    UserRequestDto userRequestDto =
            new UserRequestDto(
                    "Test User",
                    "test@example.com",
                    "password"
            );
    CrendentialsRequestDto crendentialsRequestDto = new CrendentialsRequestDto(
            "test@example.com",
            "password"
    );

    @Test
    public void saveUser_WhenUserDoesNotExist_ShouldReturnAccessToken() throws Exception {
        Mockito.when(userService.save(any())).thenReturn(new AccessToken("mockAccessToken"));

        var response = userController.saveUser(userRequestDto);

        Assertions.assertNotNull(response);
        AccessToken accessToken = (AccessToken) ((ResponseEntity<?>) response).getBody();
        Assertions.assertNotNull(accessToken);
        Assertions.assertEquals("mockAccessToken", accessToken.accessToken());
    }

    @Test
    public void saveUser_WhenUserAlreadyExists_ShouldReturnConflict() throws Exception {
        Mockito.when(userService.save(any(User.class))).thenThrow(new DuplicatedTupleException("User already exists"));

        ResponseEntity<?> response = userController.saveUser(userRequestDto);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getBody();
        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals("User already exists", responseBody.get("message"));
    }

    @Test
    public void auth_WhenUserExists_ShouldReturnAccessToken() throws Exception {
        Mockito.when(userService.authenticate(any(), any())).thenReturn(new AccessToken("mockAccessToken"));

        var response = userController.auth(crendentialsRequestDto);

        Assertions.assertNotNull(response);
        AccessToken accessToken = (AccessToken) ((ResponseEntity<?>) response).getBody();
        Assertions.assertNotNull(accessToken);
        Assertions.assertEquals("mockAccessToken", accessToken.accessToken());
    }

    @Test
    public void auth_WhenUserDoesNotExist_ShouldReturnUnauthorized() throws Exception {
        Mockito.when(userService.authenticate(any(), any())).thenReturn(null);

        var response = userController.auth(crendentialsRequestDto);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, ((ResponseEntity<?>) response).getStatusCode());
    }

}